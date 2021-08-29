package org.example.springdata.service;

import org.example.springdata.domain.Pet;
import org.example.springdata.domain.PetRepository;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Override
    public Iterable<Pet> findAll() {
        return petRepository.findAll();
    }

    @Override
    public void deleteAll() {
        petRepository.deleteAll();
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
       throw new RuntimeException(pet.toString());
    }
}
