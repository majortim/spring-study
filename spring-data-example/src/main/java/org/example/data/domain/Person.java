package org.example.data.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class Person implements Persistable<Long> {
    @Id
    private final Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Transient
    private Boolean isNew = false;

    public Person(Long id, String name, Gender gender) {
        this.id = id;
        this.name = name;
        this.gender = gender;
    }

    public void update(String name, Gender gender) {
        this.name = name;
        this.gender = gender;
        this.isNew = false;
    }

    public static Person withId(Long id, String name, Gender sex) {
        Person person = new Person(id, name, sex);

        person.isNew = true;
        return person;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public enum Gender {
        M("남성"),
        F("여성");

        private final String value;

        Gender(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
