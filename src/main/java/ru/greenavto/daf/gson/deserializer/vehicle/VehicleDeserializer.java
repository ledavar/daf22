package ru.greenavto.daf.gson.deserializer.vehicle;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.greenavto.daf.model.vehicle.AttributeGroup;
import ru.greenavto.daf.model.vehicle.Vehicle;

import java.lang.reflect.Type;

public class VehicleDeserializer implements JsonDeserializer<Vehicle> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonDeserializer.class);

    @Override
    public Vehicle deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        Vehicle vehicle = new Vehicle();

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonObject attributeGroups = jsonObject.get("List").getAsJsonObject();
        JsonArray attributeGroupArray = attributeGroups.getAsJsonArray("AttributeGroup");

        for (JsonElement attributeGroup : attributeGroupArray) {
            vehicle.addAttributeGroup(jsonDeserializationContext.deserialize(attributeGroup, AttributeGroup.class));
        }

        // VehicleName
        vehicle.setVehicleName(jsonObject.get("VehicleName").getAsString());

        // VehicleSerie
        vehicle.setVehicleSerie(jsonObject.get("VehicleSerie").getAsString());

        return vehicle;
    }
}
