package org.example.data.service;

import org.example.data.config.AppConfig;
import org.example.data.domain.Pet;
import org.example.data.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("mybatis")
@SpringJUnitConfig(AppConfig.class)
class PetServiceTests {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PetService petService;
    @Autowired
    PetRepository petRepository;

    @Test
    void findAllTest() {
        logger.debug("repo: {}", petRepository);
        List<Pet> result = new ArrayList<>();
        petService.findAll().iterator().forEachRemaining(result::add);

        assertNotNull(result);
        assertNotEquals(0, result.size());
    }

    @Test
    void testTransactionTest() {
        petService.deleteAll();

        assertEquals(0, petService.count());

        Pet pet1 = Pet.builder()
                .name("뚱식이")
                .owner("문세윤")
                .species("챠우챠우")
                .sex(Pet.Sex.M)
                .birth(LocalDate.of(2019, 3, 2))
                .build();
        Pet pet2 = Pet.builder()
                .name("복순이")
                .owner("김태리")
                .species("콜리")
                .sex(Pet.Sex.F)
                .birth(LocalDate.of(2004, 9, 7))
                .death(LocalDate.of(2020, 11, 2))
                .build();

        petService.save(pet1);
        petService.save(pet2);
        //petService.testTransaction(pet2);

        Logger logger = LoggerFactory.getLogger(this.getClass());
        System.out.println("TEST!!");

        petService.findAll();
        logger.debug("petRepository: {}", petRepository);
        petService.deleteByName(pet2.getName());

        assertEquals(1, petService.count());
    }

    @Test
    void findAndDeleteTest() {
        Pet pet = Pet.builder()
                .name("장군")
                .owner("김종민")
                .species("퍼그")
                .sex(Pet.Sex.M)
                .birth(LocalDate.of(2019, 3, 2))
                .death(LocalDate.of(2020, 5, 2))
                .build();


        Pet saved = petService.save(pet);

        assertNotNull(saved);
        assertTrue(petService.findAllByName("장군").iterator().hasNext());
        assertFalse(petService.findAllByName("멍군").iterator().hasNext());

        logger.debug("id: {}", saved.getId());

        Optional<Pet> found = petService.findById(saved.getId());
        found.ifPresent(p-> {
            assertEquals(saved.getOwner(), p.getOwner());
            assertEquals(saved.getSpecies(), p.getSpecies());
            assertEquals(saved.getBirth(), p.getBirth());
            assertEquals(saved.getDeath(), p.getDeath());
        });

        Pet toBe = Pet.builder()
                .id(saved.getId())
                .name("멍군")
                .owner(saved.getOwner())
                .species(saved.getSpecies())
                .sex(saved.getSex())
                .birth(saved.getBirth())
                .death(saved.getDeath())
                .buildWithId();

        petService.save(toBe);

        petService.findById(toBe.getId()).ifPresent(p -> assertEquals(toBe.getName(), p.getName()));

        petService.deleteById(saved.getId());
        assertNull(petService.findById(saved.getId()).orElse(null));
    }

    @Test
    void throwExceptionTest() {

        petService.deleteAll();

        Pet pet = Pet.builder()
                .name("풍이")
                .owner("금순이")
                .species("진돗개")
                .sex(Pet.Sex.M)
                .birth(LocalDate.of(2018, 2, 6))
                .build();

        Pet saved = petService.save(pet);

        try {
            petService.testTransaction(pet);
        } catch (RuntimeException e) {
            logger.info("Spring Data JDBC 에서는 Exception 을 catch 하면 그 이후에도 Exception 이 발생한 트랜잭션을 사용할 수 있다.");
        }

        assertNotNull(petService.findById(saved.getId()).orElse(null));
        assertEquals("수컷", pet.getSex().getValue());
        assertEquals(1, petService.count());
    }
}