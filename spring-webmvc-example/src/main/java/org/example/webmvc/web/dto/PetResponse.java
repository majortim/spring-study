package org.example.webmvc.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.example.webmvc.convert.PetSexSerializer;
import org.example.webmvc.domain.Pet;

import java.time.LocalDate;

@SuppressWarnings("unused")
public class PetResponse {

    private final Long id;
    private final String name;
    private final String owner;
    private final String species;
    @JsonSerialize(using = PetSexSerializer.class)
    private final Pet.Sex sex;
    private final LocalDate birth;
    private final LocalDate death;


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
