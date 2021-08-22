package org.example.webmvc.web.dto;

import org.example.webmvc.domain.Pet;

import java.time.LocalDate;

public class PetDto {
    private String name;
    private String owner;
    private String species;
    private Pet.Sex sex;
    private LocalDate birth;
    private LocalDate death;

    public PetDto() {

    }

    public PetDto(String name, String owner, String species, Pet.Sex sex, LocalDate birth, LocalDate death) {
        this.name = name;
        this.owner = owner;
        this.species = species;
        this.sex = sex;
        this.birth = birth;
        this.death = death;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setSex(Pet.Sex sex) {
        this.sex = sex;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public void setDeath(LocalDate death) {
        this.death = death;
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

    public Pet.Sex getSex() {
        return sex;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public LocalDate getDeath() {
        return death;
    }

    public Pet toEntity() {
        return Pet.builder()
                .name(name)
                .owner(owner)
                .species(species)
                .sex(sex)
                .birth(birth)
                .death(death)
                .build();
    }
}
