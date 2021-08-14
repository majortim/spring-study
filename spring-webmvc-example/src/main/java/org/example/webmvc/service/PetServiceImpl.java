package org.example.webmvc.service;

import org.example.webmvc.domain.Pet;
import org.example.webmvc.domain.PetRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public <S extends Pet> S save(S pet) {
        return petRepository.save(pet);
    }

    @Override
    public <S extends Pet> Iterable<S> saveAll(Iterable<S> pets) {
        return petRepository.saveAll(pets);
    }

    @Override
    public boolean existsById(Long id) {
        return petRepository.existsById(id);
    }

    @Override
    public Optional<Pet> findById(Long id) {
        return petRepository.findById(id);
    }

    @Override
    public Iterable<Pet> findAll() {
        return petRepository.findAll();
    }

    @Override
    public Page<Pet> findAll(Pageable pageable) {
        return petRepository.findAll(pageable);
    }

    @Override
    public Iterable<Pet> findAll(Sort sort) {
        return petRepository.findAll(sort);
    }

    @Override
    public Iterable<Pet> findAllById(Iterable<Long> ids) {
        return petRepository.findAllById(ids);
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
    public void deleteAllById(Iterable<? extends Long> ids) {
        petRepository.deleteAllById(ids);
    }

    @Override
    public void deleteAll(Iterable<? extends Pet> pets) {
        petRepository.deleteAll(pets);
    }

    @Override
    public void deleteAll() {
        petRepository.deleteAll();
    }
}
