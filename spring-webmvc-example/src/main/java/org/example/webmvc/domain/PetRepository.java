package org.example.webmvc.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PetRepository extends PagingAndSortingRepository<Pet, Long> {
    List<Pet> findAll();
    Page<Pet> findAll(Pageable pageable);
    List<Pet> findAll(Sort sort);
    List<Pet> findAllById(Iterable<Long> ids);
    List<Pet> findByNameAndOwnerAllIgnoreCase(String name, String owner);

}
