package org.example.webmvc.web.dto;

import org.example.webmvc.domain.Pet;

import java.time.LocalDate;

public class PetResponseDto {

    private final String name;
    private final String owner;
    private final String species;
    private final String sex;
    private final LocalDate birth;
    private final LocalDate death;

    public PetResponseDto(Pet pet) {
        this.name = pet.getName();
        this.owner = pet.getOwner();
        this.species = pet.getSpecies();
        this.sex = pet.getSex().getValue();
        this.birth = pet.getBirth();
        this.death = pet.getDeath();
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
