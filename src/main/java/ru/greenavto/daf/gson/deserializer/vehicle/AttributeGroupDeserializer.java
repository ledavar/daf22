package ru.greenavto.daf.gson.deserializer.vehicle;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.greenavto.daf.model.vehicle.Attribute;
import ru.greenavto.daf.model.vehicle.AttributeGroup;
import ru.greenavto.daf.model.vehicle.Description;

import java.lang.reflect.Type;

public class AttributeGroupDeserializer implements JsonDeserializer<AttributeGroup> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttributeGroupDeserializer.class);

    @Override
    public AttributeGroup deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        AttributeGroup attributeGroup = new AttributeGroup();

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        // Attributes
        JsonArray attribs = jsonObject.getAsJsonArray("Attributes");
        for (JsonElement attrib : attribs) {
            attributeGroup.addAttribute(jsonDeserializationContext.deserialize(attrib, Attribute.class));
        }

        // Description
        attributeGroup.setDescription(jsonDeserializationContext.deserialize(jsonObject.get("Description"), Description.class));

        // Id
        attributeGroup.setId(jsonObject.get("Id").getAsString());

        return attributeGroup;
    }
}
