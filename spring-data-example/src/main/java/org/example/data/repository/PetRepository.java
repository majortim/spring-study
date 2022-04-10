package org.example.data.repository;

import org.example.data.domain.Pet;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface PetRepository extends Repository<Pet, Long> {
    Long count();
    Iterable<Pet> findAll();
    Iterable<Pet> findByName(String name);
    Optional<Pet> findById(Long id);
    void deleteAll();
    void deleteAll(Iterable<Pet> iterable);
    void deleteById(Long id);
    default void deleteByName(String name) {
        Iterable<Pet> petIterable = findByName(name);
        deleteAll(petIterable);
    }
    <S extends Pet> S save(S pet);
}
