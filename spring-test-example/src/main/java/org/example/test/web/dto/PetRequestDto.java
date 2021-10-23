package org.example.test.web.dto;

import org.example.test.domain.Pet;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class PetRequestDto {
    @Size(max = 10, message = "이름을 10자 이내로 입력해주세요.")
    @NotBlank(message = "이름을 작성해주세요.")
    private String name;
    @Size(max = 20, message = "소유주 이름을 20자 이내로 입력해주세요.")
    @NotBlank(message = "소유주 이름을 작성해주세요.")
    private String owner;
    @Size(max = 30, message = "종을 30자 이내로 입력해주세요.")
    @NotBlank(message = "종을 작성해주세요.")
    private String species;
    @NotNull(message = "성별을 선택해주세요.")
    private Pet.Sex sex;
    private LocalDate birth;
    private LocalDate death;

    @SuppressWarnings("unused")
    //public PetRequestDto(){ }

    /*
    public PetRequestDto(String name, String owner, String species, Pet.Sex sex, LocalDate birth, LocalDate death) {
        this.name = name;
        this.owner = owner;
        this.species = species;
        this.sex = sex;
        this.birth = birth;
        this.death = death;
    }
    */

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

//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public void setOwner(String owner) {
//        this.owner = owner;
//    }
//
//    public void setSpecies(String species) {
//        this.species = species;
//    }
//
//    public void setSex(Pet.Sex sex) {
//        this.sex = sex;
//    }
//
//    public void setBirth(LocalDate birth) {
//        this.birth = birth;
//    }
//
//    public void setDeath(LocalDate death) {
//        this.death = death;
//    }

    @Override
    public String toString() {
        return String.format("PetRequestDto (name = %s, owner = %s, species = %s, sex = %s, birth = %s, death = %s)", name, owner, species, sex, birth, death);
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
