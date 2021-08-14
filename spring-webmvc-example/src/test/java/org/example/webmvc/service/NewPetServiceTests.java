package org.example.webmvc.service;

import org.example.webmvc.config.RootConfig;
import org.example.webmvc.domain.Pet;
import org.example.webmvc.domain.PetRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class NewPetServiceTests {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PetRepository repository;

    @Test
    public void findAllTest() {
        Pet pet1 = Pet.builder()
                .name("뚱식이")
                .owner("문세윤")
                .species("챠우챠우")
                .sex("M")
                .birth(LocalDate.of(2019, 3, 2))
                .build();
        Pet pet2 = Pet.builder()
                .name("복순이")
                .owner("김태리")
                .species("콜리")
                .sex("F")
                .birth(LocalDate.of(2004,9,7))
                .death(LocalDate.of(2020, 11,2))
                .build();

        repository.save(pet1);
        repository.save(pet2);

        logger.debug("list: {}", repository.findAll());

    }
}
