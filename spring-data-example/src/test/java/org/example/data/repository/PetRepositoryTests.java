package org.example.data.repository;

import org.example.data.config.AppConfig;
import org.example.data.domain.Pet;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("mybatis")
@SpringJUnitConfig(AppConfig.class)
public class PetRepositoryTests {
    @Autowired
    MyBatisSpringJdbcPetRepository repository;

    @Test
    void findAllTest() {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        logger.debug("repo: {}", repository);
        logger.debug("mapper namespace: {}", Pet.class.getName() + "Mapper");
        logger.debug("simple name: {}", Pet.class.getSimpleName());
        List<Pet> result = new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(result::add);

        assertNotNull(result);
        assertNotEquals(0, result.size());
    }
}
