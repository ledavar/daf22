package ru.greenavto.daf.gson.serializer.vehicle;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.greenavto.daf.model.vehicle.Attribute;

import java.lang.reflect.Type;

public class AttributeSerializer implements JsonSerializer<Attribute> {

    private static final String VEHICLE_DATA_ID = "VehicleData_ID";
    private static final String VEHICLE_DATA_NAME = "VehicleData_Name";
    private static final String VEHICLE_DATA_VALUE = "VehicleData_Value";

    @Override
    public JsonElement serialize(Attribute attribute, Type type, JsonSerializationContext jsonSerializationContext) {

        JsonObject result = new JsonObject();

        result.addProperty(VEHICLE_DATA_ID, attribute.getId());
        result.addProperty(VEHICLE_DATA_NAME, attribute.getName());
        result.addProperty(VEHICLE_DATA_VALUE, attribute.getValue());

        return result;
    }

}
