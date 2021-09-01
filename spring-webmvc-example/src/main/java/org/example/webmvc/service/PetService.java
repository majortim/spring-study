package org.example.webmvc.service;

import org.example.webmvc.domain.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PetService {

    <S extends Pet> S save(S pet);
    Optional<Pet> findById(Long id);
    List<Pet> findAll();
    Page<Pet> findAll(Pageable pageable);
    long count();
    void deleteById(Long id);
    void delete(Pet pet);
    void deleteAll();
}
