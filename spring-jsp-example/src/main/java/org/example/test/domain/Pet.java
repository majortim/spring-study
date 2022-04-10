package org.example.test.domain;


import java.time.LocalDate;

@SuppressWarnings("unused")
public class Pet {
    private Long id;
    private String name;
    private String owner;
    private String species;
    private Sex sex;
    private LocalDate birth;
    private LocalDate death;

    public Pet(long id, String name, String owner, String species, Sex sex, LocalDate birth, LocalDate death) {
        this(name, owner, species, sex, birth, death);
        this.id = id;
    }

    public Pet(String name, String owner, String species, Sex sex, LocalDate birth, LocalDate death) {
        update(name, owner, species, sex, birth, death);
    }

    public void update(String name, String owner, String species, Sex sex, LocalDate birth, LocalDate death) {
        this.name = name;
        this.owner = owner;
        this.species = species;
        this.sex = sex;
        this.birth = birth;
        this.death = death;
    }

    public void update(Pet pet) {
        this.name = pet.name;
        this.owner = pet.owner;
        this.species = pet.species;
        this.sex = pet.sex;
        this.birth = pet.birth;
        this.death = pet.death;
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

    public LocalDate getBirth() {
        return birth;
    }

    public LocalDate getDeath() {
        return death;
    }


    @Override
    public String toString() {
        return String.format("Pet (id = %d, name = %s, owner = %s, species = %s, sex = %s, birth = %s, death = %s)", id, name, owner, species, sex, birth, death);
    }

    public enum Sex  {
        M("수컷"),
        F("암컷");

        private final String value;

        Sex(String value) {
            this.value =value;
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

        public PetBuilder id(long id) {
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
            return String.format("PetBuilder(id =%d, name = %s, owner = %s, species = %s, sex = %s, birth = %s, death = %s)", id, name, owner, species, sex, birth, death);
        }

        public Pet build() {
            return new Pet(name, owner, species, sex, birth, death);
        }
    }
}
