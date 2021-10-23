package org.example.test.web.dto;

import org.example.test.domain.Pet;

import java.time.LocalDate;

public class PetResponseDto {

    private final Long id;
    private final String name;
    private final String owner;
    private final String species;
    private final String sex;
    private final LocalDate birth;
    private final LocalDate death;

    public PetResponseDto(Pet pet) {
        this.id = pet.getId();
        this.name = pet.getName();
        this.owner = pet.getOwner();
        this.species = pet.getSpecies();
        this.sex = pet.getSex().getValue();
        this.birth = pet.getBirth();
        this.death = pet.getDeath();
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

    public String getSex() {
        return sex;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public LocalDate getDeath() {
        return death;
    }
}
