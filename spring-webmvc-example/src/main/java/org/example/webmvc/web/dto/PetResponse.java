package org.example.webmvc.web.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.example.webmvc.convert.PetSexConverter;
import org.example.webmvc.domain.Pet;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@SuppressWarnings("unused")
public class PetResponse {

    @Id
    private final Long id;
    private final String name;
    private final String owner;
    private final String species;
    @JsonSerialize(converter = PetSexConverter.class)
    private final Pet.Sex sex;
    private final LocalDate birth;
    private final LocalDate death;

    public PetResponse(Long id, String name, String owner, String species, Pet.Sex sex, LocalDate birth, LocalDate death) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.species = species;
        this.sex = sex;
        this.birth = birth;
        this.death = death;
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
        return new PetResponse(pet.getId(), pet.getName(), pet.getOwner(), pet.getSpecies(), pet.getSex(), pet.getBirth(), pet.getDeath());
    }

    @Override
    public String toString() {
        return String.format("PetResponse(id =%d, name = %s, owner = %s, species = %s, sex = %s, birth = %s, death = %s)", id, name, owner, species, sex, birth, death);
    }
}
