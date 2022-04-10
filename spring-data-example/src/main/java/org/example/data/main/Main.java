package org.example.data.main;

import org.example.data.config.AppConfig;
import org.example.data.domain.Pet;
import org.example.data.service.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;

public class Main {
    public static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        PetService petService = ctx.getBean(PetService.class);
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
                .birth(LocalDate.of(2004,9,7))
                .death(LocalDate.of(2020, 11,2))
                .build();

        petService.save(pet1);
        petService.save(pet2);

        logger.debug("list: {}", petService.findAll());
    }
}
