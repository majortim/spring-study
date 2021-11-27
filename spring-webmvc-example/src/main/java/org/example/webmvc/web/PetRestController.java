package org.example.webmvc.web;

import org.example.webmvc.domain.Pet;
import org.example.webmvc.service.PetService;
import org.example.webmvc.web.dto.PetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;


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
        Page<PetResponse> pages = petService.findAll(pageable).map(PetResponse::from);

        resultMap.put("pageRequest", pages.getPageable());
        resultMap.put("count", pages.getTotalElements());
        resultMap.put("totalPages", pages.getTotalPages());
        resultMap.put("list", pages.toList());

        return ResponseEntity.ok(resultMap);
    }

    @GetMapping(value = "/pets/all", produces = "application/json; charset=utf-8")
    public ResponseEntity<List<PetResponse>> all() {
        return ResponseEntity.ok(petService.findAll().stream().map(PetResponse::from).collect(Collectors.toList()));
    }

    @GetMapping(value = "/pets/{petId}", produces = "application/json; charset=utf-8")
    public PetResponse get(@PathVariable("petId") Pet pet) {
        return Optional.of(pet).map(PetResponse::from).orElse(null);
    }
}
