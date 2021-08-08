package org.example.webmvc.domain;

import java.util.List;

public interface PetRepository {
    long count();
    List<Pet> findAllByName(String name);
    List<Pet> findAll();
    void deleteAll();
    void deleteByName(String name);
    Pet save(Pet pet);
}
