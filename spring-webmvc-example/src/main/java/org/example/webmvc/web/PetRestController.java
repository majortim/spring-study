package org.example.webmvc.web;

import org.example.webmvc.service.PetService;
import org.example.webmvc.web.dto.PetDto;
import org.example.webmvc.web.dto.PetResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@RestController
public class PetRestController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PetService petService;

    public PetRestController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping(value = "/pets", produces = "application/json; charset=utf-8")
    public ResponseEntity<Map<String, Object>> pets(@RequestParam Map<String, String> param, Pageable pageable) {
        Map<String, Object> resultMap = new HashMap<>();
        Page<PetResponseDto> pages = petService.findAll(pageable);


        resultMap.put("pageRequest", pages.getPageable());
        resultMap.put("count", pages.getTotalElements());
        resultMap.put("totalPages", pages.getTotalPages());
        resultMap.put("list", pages.toList());

        return ResponseEntity.ok(resultMap);
    }

    @GetMapping(value = "/pets/all", produces = "application/json; charset=utf-8")
    public ResponseEntity<List<PetResponseDto>> all() {
        return ResponseEntity.ok(petService.findAll());
    }

    @GetMapping("/pets/{petId}/edit")
    public String initUpdateForm(@PathVariable("petId") long petId, ModelMap model) {
        PetResponseDto pet = petService.findById(petId).orElse(null);
        model.put("pet", pet);

        return "register";
    }

    @PostMapping("/pets/{petId}/edit")
    public String processUpdateForm(PetDto petDto, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            logger.error("");
            logger.debug("error field: {}", Objects.requireNonNull(result.getFieldError()).getArguments());
            model.put("error", result.getFieldError().getField());
        } else {
            this.petService.save(petDto.toEntity());
        }

        return "pets";
    }
}
