package org.example.test.convert;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.example.test.domain.Pet;

import java.io.IOException;

public class PetSexSerializer extends StdSerializer<Pet.Sex> {

    protected PetSexSerializer(Class<Pet.Sex> t) {
        super(t);
    }
    public PetSexSerializer(){
        this(Pet.Sex.class);
    }

    @Override
    public void serialize(Pet.Sex sex, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(sex.getValue());
    }
}
