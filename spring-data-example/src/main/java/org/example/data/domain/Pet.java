package org.example.data.domain;

import org.springframework.data.annotation.Id;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

public class Pet {
    @Id
    private final Long id;
    private final String name;
    private final String owner;
    private final String species;
    @Enumerated(EnumType.STRING)
    private final Sex sex;
    private final LocalDate birth;
    private final LocalDate death;

    public Pet(Long id, String name, String owner, String species, Sex sex, LocalDate birth, LocalDate death) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.species = species;
        this.sex = sex;
        this.birth = birth;
        this.death = death;
    }

    public Pet withId(Long id) {
        return new Pet(id, this.name, this.owner, this.species, this.sex, this.birth, death);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public String getSpecies() {
        return species;
    }

    public Sex getSex() {
        return sex;
    }

    @Override
    public String toString() {
        return String.format("Pet (id = %d, name = %s, owner = %s, species = %s, sex = %s, birth = %s, death = %s)", id, name, owner, species, sex, birth, death);
    }

    public LocalDate getBirth() {
        return birth;
    }

    public LocalDate getDeath() {
        return death;
    }

    public enum Sex  {
        M("수컷"),
        F("암컷");

        private final String value;

        Sex(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    public static PetBuilder builder() {
        return new PetBuilder();
    }

    public static class PetBuilder {
        private Long id;
        private String name;
        private String owner;
        private String species;
        private Sex sex;
        private LocalDate birth;
        private LocalDate death;

        private PetBuilder() {}

        public PetBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PetBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PetBuilder owner(String owner) {
            this.owner = owner;
            return this;
        }

        public PetBuilder species(String species) {
            this.species = species;
            return this;
        }

        public PetBuilder sex(Sex sex) {
            this.sex = sex;
            return this;
        }

        public PetBuilder birth(LocalDate birth) {
            this.birth = birth;
            return this;
        }

        public PetBuilder death(LocalDate death) {
            this.death = death;
            return this;
        }

        @Override
        public String toString() {
            return String.format("PetBuilder(name = %s, owner = %s, species = %s, sex = %s, birth = %s, death = %s)", name, owner, species, sex, birth, death);
        }

        public Pet build() {
            return new Pet(null, name, owner, species, sex, birth, death);
        }

        public Pet buildWithId() {
            return new Pet(id, name, owner, species, sex, birth, death);
        }
    }
}
