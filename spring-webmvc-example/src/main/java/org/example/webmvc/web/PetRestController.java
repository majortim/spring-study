package org.example.webmvc.web;

import org.example.webmvc.domain.Pet;
import org.example.webmvc.service.PetService;
import org.example.webmvc.web.dto.PetRequestDto;
import org.example.webmvc.web.dto.PetResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
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

    @GetMapping("/pets/test")
    public ResponseEntity<List<PetResponseDto>> test() {
        return ResponseEntity.status(201)
                .header(HttpHeaders.CONTENT_LANGUAGE, Locale.KOREA.toLanguageTag())
                .contentType(MediaType.APPLICATION_JSON)
                .body( petService.findAll().stream()
                        .map(PetResponseDto::new).collect(Collectors.toList()) );
    }

    @GetMapping("/pets/test2")
    public ResponseEntity<List<PetResponseDto>> test2(PetRequestDto petRequestDto) {
        logger.debug("petRequestDto: {}", petRequestDto);
        logger.debug("name: {}", petRequestDto.getName());
        logger.debug("owner: {}", petRequestDto.getOwner());


        return ResponseEntity.status(201)
                .header(HttpHeaders.CONTENT_LANGUAGE, Locale.KOREA.toLanguageTag())
                .contentType(MediaType.APPLICATION_JSON)
                .body( petService.findByNameAndOwnerAllIgnoreCase(petRequestDto.getName(), petRequestDto.getOwner()).stream()
                        .map(PetResponseDto::new).collect(Collectors.toList()) );
    }



    @RequestMapping(value = "/pets/test3", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<List<PetResponseDto>> test3(@RequestBody PetRequestDto petRequestDto) {
        logger.debug("requestBody: {}", petRequestDto);
        logger.debug("body name: {}", petRequestDto.getName());
        logger.debug("body owner: {}", petRequestDto.getOwner());


        return ResponseEntity.status(201)
                .header(HttpHeaders.CONTENT_LANGUAGE, Locale.KOREA.toLanguageTag())
                .header(HttpHeaders.CONTENT_LANGUAGE, Locale.KOREA.toLanguageTag())
                .contentType(MediaType.APPLICATION_JSON)
                .body( petService.findByNameAndOwnerAllIgnoreCase(petRequestDto.getName(), petRequestDto.getOwner()).stream()
                        .map(PetResponseDto::new).collect(Collectors.toList()) );
    }

    @GetMapping(value = "/pets/{petId}", produces = "application/json; charset=utf-8")
    public PetResponseDto get(@PathVariable("petId") Pet pet) {
        return Optional.of(pet).map(PetResponseDto::new).orElse(null);
    }

    @GetMapping(value = "/pets/{petId}/xml",  produces = "application/xml; charset=utf-8")
    public PetResponseDto getXml(@PathVariable("petId") Pet pet) {
        return Optional.of(pet).map(PetResponseDto::new).orElse(null);
    }
}
