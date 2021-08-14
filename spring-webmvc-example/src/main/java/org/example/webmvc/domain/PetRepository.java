package org.example.webmvc.domain;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface PetRepository extends PagingAndSortingRepository<Pet, Long> {
}
