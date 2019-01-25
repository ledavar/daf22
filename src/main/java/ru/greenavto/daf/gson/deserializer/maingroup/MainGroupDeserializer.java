package ru.greenavto.daf.gson.deserializer.maingroup;

import com.google.gson.*;
import ru.greenavto.daf.model.MainGroup;

import java.lang.reflect.Type;

public class MainGroupDeserializer implements JsonDeserializer<MainGroup> {

    @Override
    public MainGroup deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        MainGroup mainGroup = new MainGroup(jsonObject.get("ID").getAsString(), jsonObject.get("Description").getAsString());

        return mainGroup;
    }


}
