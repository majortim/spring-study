package org.example.data.service;

import org.example.data.domain.Pet;

import java.util.Optional;

public interface PetService {
    Long count();
    Iterable<Pet> findAll();
    Iterable<Pet> findAllByName(String name);
    Optional<Pet> findById(Long id);
    void deleteAll();
    void deleteById(Long id);
    void deleteByName(String name);
    Pet save(Pet pet);
    void testTransaction(Pet pet);
}
