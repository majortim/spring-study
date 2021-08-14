package org.example.webmvc.web;

import org.example.webmvc.domain.Pet;
import org.example.webmvc.service.PetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PetRestController {
    private PetService petService;

    public PetRestController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping(value = "/pets/list", produces = "application/json; charset=utf-8")

    public ResponseEntity<Iterable<Pet>> list() {
        Iterable<Pet> list = petService.findAll();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
