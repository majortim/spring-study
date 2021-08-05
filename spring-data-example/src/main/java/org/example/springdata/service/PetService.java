package org.example.springdata.service;

import org.example.springdata.domain.Pet;

import java.util.List;


public interface PetService {

    long count();
    List<Pet> findAllByName(String name);
    List<Pet> findAll();
    void deleteAll();
    Pet save(Pet pet);
}
