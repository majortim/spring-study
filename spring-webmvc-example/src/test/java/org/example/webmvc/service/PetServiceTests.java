package org.example.webmvc.service;

import org.example.webmvc.config.RootConfig;
import org.example.webmvc.domain.Pet;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
public class PetServiceTests {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PetService petService;

    @Test
    @Order(0)
    @Commit
    public void deleteAllTest() {
        petService.deleteAll();
        assertEquals(0, petService.count());

    }

    @Test
    @Order(2)
    @Commit
    public void deleteByIdTest() {
        petService.deleteById(1L);
        logger.debug("list: {}", petService.findAll());
    }

    @Test
    @Order(1)
    @Commit
    public void findAllTest() {
        Pet pet1 = Pet.builder()
                .name("뚱식이")
                .owner("문세윤")
                .species("챠우챠우")
                .sex("M")
                .birth(LocalDate.of(2019, 3, 2))
                //.death(LocalDate.of(2020, 11,2))
                .build();
        Pet pet2 = Pet.builder()
                .name("복순이")
                .owner("김태리")
                .species("콜리")
                .sex("F")
                .birth(LocalDate.of(2004,9,7))
                .death(LocalDate.of(2020, 11,2))
                .build();

        petService.save(pet1);
        petService.save(pet2);

        logger.debug("pet1: {}", petService.findById(1L));
        logger.debug("pet2: {}", petService.findById(2L));

        logger.debug("list: {}", petService.findAll());
        logger.debug("count: {}", petService.count());
    }
}
