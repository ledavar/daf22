package ru.greenavto.daf.gson.serializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.greenavto.daf.model.Component;

import java.lang.reflect.Type;

public class ComponentSerializer implements JsonSerializer<Component> {

    @Override
    public JsonElement serialize(Component component, Type type, JsonSerializationContext jsonSerializationContext) {
        return null;
    }
}
