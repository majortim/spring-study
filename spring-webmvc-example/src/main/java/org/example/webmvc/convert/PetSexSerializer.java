package org.example.webmvc.convert;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.example.webmvc.domain.Pet;

import java.io.IOException;

public class PetSexSerializer extends StdSerializer<Pet.Sex> {

    private PetSexSerializer(Class<Pet.Sex> t) {
        super(t);
    }
    @SuppressWarnings("unused")
    private PetSexSerializer(){
        this(Pet.Sex.class);
    }

    @Override
    public void serialize(Pet.Sex sex, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(sex.getValue());
    }
}
