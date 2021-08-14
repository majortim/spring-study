package org.example.webmvc.web.dto;

import org.example.webmvc.domain.Pet;

import java.time.LocalDate;

public class PetDto {
    private String name;
    private String owner;
    private String species;
    private String sex;
    private LocalDate birth;
    private LocalDate death;

    public PetDto() {

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

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public void setDeath(LocalDate death) {
        this.death = death;
    }

    public Pet toEntity() {
        return Pet.builder()
                .name(name)
                .owner(owner)
                .sex(sex)
                .birth(birth)
                .death(death)
                .build();
    }
}
