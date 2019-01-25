package ru.greenavto.daf.gson.deserializer.vin;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.greenavto.daf.model.Vin;

import java.lang.reflect.Type;

public class VinDeserializer implements JsonDeserializer<Vin> {

    private static final Logger LOGGER = LoggerFactory.getLogger(VinDeserializer.class);

    @Override
    public Vin deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        Vin vin = new Vin(jsonObject.get("result").getAsString(), jsonObject.get("WMI").getAsString());
        return vin;
    }
}
