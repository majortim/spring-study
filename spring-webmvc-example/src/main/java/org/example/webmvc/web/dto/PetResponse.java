package org.example.webmvc.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.example.webmvc.convert.PetSexSerializer;
import org.example.webmvc.domain.Pet;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import java.time.LocalDate;

@SuppressWarnings("unused")
public class PetResponse {

    @Id
    private final Long id;
    private final String name;
    private final String owner;
    private final String species;
    @JsonSerialize(using = PetSexSerializer.class)
    private final Pet.Sex sex;
    private final LocalDate birth;
    private final LocalDate death;

    @PersistenceConstructor
    public PetResponse(Long id, String name, String owner, String species, Pet.Sex sex, LocalDate birth, LocalDate death) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.species = species;
        this.sex = sex;
        this.birth = birth;
        this.death = death;
    }

    private PetResponse(Pet pet) {
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

    public static PetResponse from(Pet pet){
        return new PetResponse(pet);
    }

    @Override
    public String toString() {
        return String.format("PetResponse(id =%d, name = %s, owner = %s, species = %s, sex = %s, birth = %s, death = %s)", id, name, owner, species, sex, birth, death);
    }
}
