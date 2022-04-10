package org.example.test.web.dto;

import org.example.test.domain.Pet;

import java.time.LocalDate;

@SuppressWarnings("unused")
public class PetResponse {

    private Long id;
    private String name;
    private String owner;
    private String species;
    private Pet.Sex sex;
    private LocalDate birth;
    private LocalDate death;

    private PetResponse(){}

    public PetResponse(Pet pet) {
        this.id = pet.getId();
        this.name = pet.getName();
        this.owner = pet.getOwner();
        this.species = pet.getSpecies();
        this.sex = pet.getSex();
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

    public Pet.Sex getSex() {
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
        return String.format("PetResponse(id =%d, name = %s, owner = %s, species = %s, sex = %s, birth = %s, death = %s)", id, name, owner, species, sex, birth, death);
    }
}
