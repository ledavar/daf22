package ru.greenavto.daf.gson.serializer.vin;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.greenavto.daf.model.Vin;

import java.lang.reflect.Type;

public class VinSerializer implements JsonSerializer<Vin> {

    private static final String RESULT = "result";
    private static final String WMI = "wmi";

    @Override
    public JsonElement serialize(Vin vin, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        result.addProperty(RESULT, vin.getResult());
        result.addProperty(WMI, vin.getWmi());
        return result;
    }
}
