package org.example.springdata.service;

import org.example.springdata.domain.Pet;
import org.example.springdata.domain.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class PetServiceImpl implements PetService {
    private PetRepository petRepository;

    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public long count() {
        return petRepository.count();
    }

    @Override
    public List<Pet> findAllByName(String name) {
        return petRepository.findAllByName(name);
    }

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public void deleteAll() {
        petRepository.deleteAll();
    }

    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }
}
