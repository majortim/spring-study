package org.example.springdata.domain;

import java.util.List;

public interface PetRepository {
    long count();
    List<Pet> findAllByName(String name);
    List<Pet> findAll();
    void deleteAll();
    Pet save(Pet pet);
}
