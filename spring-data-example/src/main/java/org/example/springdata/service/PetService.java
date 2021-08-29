package org.example.springdata.service;

import org.example.springdata.domain.Pet;

import java.util.List;

public interface PetService {

    long count();
    List<Pet> findAllByName(String name);
    Iterable<Pet> findAll();
    void deleteAll();
    void deleteByName(String name);
    Pet save(Pet pet);
    void testTransaction(Pet pet);
}
