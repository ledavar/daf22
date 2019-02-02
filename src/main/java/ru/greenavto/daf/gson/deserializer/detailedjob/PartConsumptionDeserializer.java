package ru.greenavto.daf.gson.deserializer.detailedjob;

import com.google.gson.*;
import ru.greenavto.daf.model.detailedjob.PartConsumption;

import java.lang.reflect.Type;

public class PartConsumptionDeserializer implements JsonDeserializer<PartConsumption> {

    @Override
    public PartConsumption deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        PartConsumption partConsumption = new PartConsumption();

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        partConsumption.setPartNumber(jsonObject.get("PartNumber").getAsString());
        partConsumption.setPartsClass(jsonObject.get("PartsClass").getAsString());
        partConsumption.setPartDescription(jsonObject.get("PartDescription").getAsString());
        partConsumption.setcGroupNumber(jsonObject.get("CgroupNumber").getAsString());
        partConsumption.setcGroupDescription(jsonObject.get("CgroupDescription").getAsString());
        partConsumption.setQuantity(jsonObject.get("Quantity").getAsString());
        partConsumption.setOptional(jsonObject.get("IsOptional").getAsBoolean());

        return partConsumption;
    }

}
