package org.example.webmvc.convert;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.example.webmvc.domain.Pet;

import java.util.Optional;

public class PetSexConverter extends StdConverter<Pet.Sex, String> {
    @Override
    public String convert(Pet.Sex sex) {
        return Optional.ofNullable(sex).map(Pet.Sex::getValue).orElse("");
    }
}
