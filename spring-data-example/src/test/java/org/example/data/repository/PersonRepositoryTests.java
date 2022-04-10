package org.example.data.repository;

import org.example.data.config.AppConfig;
import org.example.data.config.EmbeddedDatabaseConfig;
import org.example.data.config.JdbcConfig;
import org.example.data.config.MyBatisConfig;
import org.example.data.domain.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringJUnitConfig({AppConfig.class, JdbcConfig.class, EmbeddedDatabaseConfig.class})
class PersonRepositoryTests {
    @Autowired
    PersonRepository repository;

    @Test
    void findTest() {
        Person person = Person.withId(200L, "김점남", Person.Gender.M);
        String changeNameTo = "김점순";

        assertNotNull(repository.save(person));
        person.update(changeNameTo, Person.Gender.F);
        repository.save(person);

        repository.findById(200L).ifPresent(p -> {
            assertEquals(Person.Gender.F.getValue(), p.getGender().getValue());
            assertEquals(changeNameTo, p.getName());
        });

    }
}