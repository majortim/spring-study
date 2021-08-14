package org.example.webmvc.service;

import org.example.webmvc.domain.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public interface PetService {

    <S extends Pet> S save(S pet);
    <S extends Pet> Iterable<S> saveAll(Iterable<S> pets);
    boolean existsById(Long id);
    public Optional<Pet> findById(Long id);
    Iterable<Pet> findAll();
    Page<Pet> findAll(Pageable pageable);
    Iterable<Pet> findAll(Sort sort);
    Iterable<Pet> findAllById(Iterable<Long> ids);
    long count();
    void deleteById(Long id);
    void delete(Pet pet);
    void deleteAllById(Iterable<? extends Long> ids);
    void deleteAll(Iterable<? extends Pet> pets);
    void deleteAll();
}
