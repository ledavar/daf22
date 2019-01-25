package ru.greenavto.daf.gson.deserializer.job;

import com.google.gson.*;
import ru.greenavto.daf.model.Job;

import java.lang.reflect.Type;

public class JobDeserializer implements JsonDeserializer<Job> {

    @Override
    public Job deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        Job job = new Job();

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        job.setKey(jsonObject.get("key").getAsString());
        job.setTitle(jsonObject.get("title").getAsString());
        job.setTooltip(jsonObject.get("tooltip").getAsString());

        return job;
    }
}
