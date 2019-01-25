package ru.greenavto.daf.gson.serializer.vehicle;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.greenavto.daf.model.vehicle.Attribute;
import ru.greenavto.daf.model.vehicle.AttributeGroup;
import ru.greenavto.daf.model.vehicle.Vehicle;

import java.lang.reflect.Type;

public class VehicleSerializer implements JsonSerializer<Vehicle> {

    @Override
    public JsonElement serialize(Vehicle vehicle, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonArray jsonArray = new JsonArray();

        for (AttributeGroup attributeGroup : vehicle.getAttributeGroups()) {
            for (Attribute attribute : attributeGroup.getAttributes()) {
                jsonArray.add(jsonSerializationContext.serialize(attribute, Attribute.class));
            }
        }

        return jsonArray;
    }
}
