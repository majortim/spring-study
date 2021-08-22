package org.example.webmvc.service;

import org.example.webmvc.domain.Pet;
import org.example.webmvc.web.dto.PetResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface PetService {

    <S extends Pet> S save(S pet);
    public Optional<PetResponseDto> findById(Long id);
    List<PetResponseDto> findAll();
    Page<PetResponseDto> findAll(Pageable pageable);
    long count();
    void deleteById(Long id);
    void delete(Pet pet);
    void deleteAll();
}
