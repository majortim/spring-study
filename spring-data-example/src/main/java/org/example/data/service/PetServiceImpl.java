package org.example.data.service;

import org.example.data.domain.Pet;
import org.example.data.repository.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;

    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Long count() {
        return petRepository.count();
    }

    @Override
    public Iterable<Pet> findAll() {
        return petRepository.findAll();
    }

    @Override
    public Iterable<Pet>findAllByName(String name) {
        return petRepository.findByName(name);
    }

    @Override
    public Optional<Pet> findById(Long id) {
        return petRepository.findById(id);
    }

    @Override
    public void deleteAll() {
        petRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        petRepository.deleteById(id);
    }

    @Override
    public void deleteByName(String name) {
        petRepository.deleteByName(name);
    }

    @Override
    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    @Override
    public void testTransaction(Pet pet) {
        petRepository.save(pet);
       throw new RuntimeException("예외 테스트: " + pet);
    }
}
