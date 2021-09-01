package org.example.webmvc.web;

import org.example.webmvc.service.PetService;
import org.example.webmvc.web.dto.PetResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
public class PetRestController {
    private final PetService petService;

    public PetRestController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping(value = "/pets", produces = "application/json; charset=utf-8")
    public ResponseEntity<Map<String, Object>> pets(@RequestParam Map<String, String> param, Pageable pageable) {
        Map<String, Object> resultMap = new HashMap<>();
        Page<PetResponseDto> pages = petService.findAll(pageable).map(PetResponseDto::new);

        resultMap.put("pageRequest", pages.getPageable());
        resultMap.put("count", pages.getTotalElements());
        resultMap.put("totalPages", pages.getTotalPages());
        resultMap.put("list", pages.toList());

        return ResponseEntity.ok(resultMap);
    }

    @GetMapping(value = "/pets/all", produces = "application/json; charset=utf-8")
    public ResponseEntity<List<PetResponseDto>> all() {
        return ResponseEntity.ok(petService.findAll().stream().map(PetResponseDto::new).collect(Collectors.toList()));
    }
}
