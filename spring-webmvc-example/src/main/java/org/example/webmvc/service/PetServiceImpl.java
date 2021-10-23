package org.example.webmvc.service;

import org.example.webmvc.domain.Pet;
import org.example.webmvc.domain.PetRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class PetServiceImpl implements PetService {
    private final PetRepository petRepository;

    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public <S extends Pet> S save(S pet) {
        return petRepository.save(pet);
    }

    @Override
    public Optional<Pet> findById(Long id) {
        return petRepository.findById(id);
    }

    @Override
    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    @Override
    public Page<Pet> findAll(Pageable pageable) {
        return petRepository.findAll(pageable);
    }

    @Override
    public List<Pet> findByNameAndOwnerAllIgnoreCase(String name, String owner) {
        return petRepository.findByNameAndOwnerAllIgnoreCase(name, owner);
    }

    @Override
    public long count() {
        return petRepository.count();
    }

    @Override
    public void deleteById(Long id) {
        petRepository.deleteById(id);
    }

    @Override
    public void delete(Pet pet) {
        petRepository.delete(pet);
    }

    @Override
    public void deleteAll() {
        petRepository.deleteAll();
    }
}
