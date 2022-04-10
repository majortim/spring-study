package org.example.webmvc.domain;

import org.example.webmvc.web.dto.PetResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PetRepository extends PagingAndSortingRepository<Pet, Long> {
    PetResponse getById(Long id);
    List<Pet> findAll();
    Page<Pet> findAll(Pageable pageable);

}
