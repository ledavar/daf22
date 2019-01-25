package ru.greenavto.daf.gson.deserializer.vehicle;

import com.google.gson.*;
import ru.greenavto.daf.model.vehicle.Description;

import java.lang.reflect.Type;

public class DescriptionDeserializer implements JsonDeserializer<Description> {

    @Override
    public Description deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Description description = new Description();

        JsonArray jsonArray = jsonElement.getAsJsonArray();
        JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();

        description.setTitle(jsonObject.get("Value").getAsString());

        return description;
    }
}
