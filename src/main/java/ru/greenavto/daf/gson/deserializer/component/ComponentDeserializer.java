package ru.greenavto.daf.gson.deserializer.component;

import com.google.gson.*;
import ru.greenavto.daf.model.Component;

import java.lang.reflect.Type;

public class ComponentDeserializer implements JsonDeserializer<Component> {

    @Override
    public Component deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        Component component = new Component();

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        component.setId(jsonObject.get("ID").getAsString());
        component.setMainGroupId(jsonObject.get("MainGroupId").getAsString());
        component.setComponentReference(jsonObject.get("ComponentReference").getAsString());
        component.setVersion(jsonObject.get("Version").getAsString());
        component.setIndex(jsonObject.get("Index").getAsString()); // "drawingIndex" in URI
        component.setDescription(jsonObject.get("Description").getAsString());
        return component;
    }
}
