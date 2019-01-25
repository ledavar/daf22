package ru.greenavto.daf.gson.deserializer.vehicle;

import com.google.gson.*;
import ru.greenavto.daf.model.vehicle.Attribute;

import java.lang.reflect.Type;

public class AttributeDeserializer implements JsonDeserializer<Attribute> {


    @Override
    public Attribute deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        Attribute attribute = new Attribute();

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        attribute.setValue(jsonObject.get("Value").getAsString());
        attribute.setSecurityLevel(jsonObject.get("SecurityLevel").getAsInt());
        attribute.setId(jsonObject.get("Id").getAsString());
        attribute.setName(jsonObject.get("Name").getAsString());

        return attribute;
    }


}
