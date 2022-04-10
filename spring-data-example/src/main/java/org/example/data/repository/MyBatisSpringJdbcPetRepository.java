package org.example.data.repository;

import org.example.data.domain.Pet;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;

@Profile("mybatis")
@Primary
public interface MyBatisSpringJdbcPetRepository extends CrudRepository<Pet, Long> {
}
