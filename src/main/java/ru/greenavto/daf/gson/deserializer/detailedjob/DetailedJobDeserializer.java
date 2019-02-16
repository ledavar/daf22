package ru.greenavto.daf.gson.deserializer.detailedjob;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.greenavto.daf.model.detailedjob.DetailedJob;
import ru.greenavto.daf.model.detailedjob.PartConsumption;

import javax.xml.soap.Detail;
import java.lang.reflect.Type;

public class DetailedJobDeserializer implements JsonDeserializer<DetailedJob> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DetailedJobDeserializer.class);

    @Override
    public DetailedJob deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        DetailedJob detailedJob = new DetailedJob();

        JsonObject jsonObject = jsonElement.getAsJsonObject();

        detailedJob.setTitle(jsonObject.get("title").getAsString());
        detailedJob.setHasPartsViewer(jsonObject.get("haspartsviewer").getAsBoolean());
        detailedJob.setTooltip(jsonObject.get("tooltip").getAsString());
        detailedJob.setKey(jsonObject.get("key").getAsString());
        detailedJob.setComponentRef(jsonObject.get("componentref").getAsString());
        detailedJob.setComponentRefVersion(jsonObject.get("componentrefversion").getAsString());
        detailedJob.setDuration(jsonObject.get("duration").getAsDouble());
        detailedJob.setDurationTime(jsonObject.get("durationTime").getAsString());
        detailedJob.setFolder(jsonObject.get("folder").getAsBoolean());
        detailedJob.setExpanded(jsonObject.get("expanded").getAsBoolean());
        detailedJob.setLazy(jsonObject.get("lazy").getAsBoolean());

        // will it work recursively??? NO!
   /*     JsonArray children = jsonObject.get("children").getAsJsonArray();
        LOGGER.debug("CHILDREN {}", children);
        if (children.isJsonArray()) {
            for (JsonElement child : children) {
                if (child != null) {
                    LOGGER.debug("CHILD {}", child);
                    DetailedJob detailedJobChild = jsonDeserializationContext.deserialize(child, DetailedJob.class);
                    detailedJob.addChild(detailedJobChild);
                }
            }
        }*/


        while (detailedJob.isFolder()) {
            JsonArray newRoot = jsonObject.get("children").getAsJsonArray();
            LOGGER.debug("CHILD {}", newRoot);
            DetailedJob child = jsonDeserializationContext.deserialize(newRoot, DetailedJob.class);
            child.addChild(child);
        }


        // PartConsumption
        JsonArray parts = jsonObject.get("partsconsumption").getAsJsonArray();
        for (JsonElement part : parts) {
            if (part != null) {
                PartConsumption partConsumption = jsonDeserializationContext.deserialize(part, PartConsumption.class);
                detailedJob.addPartConsumption(partConsumption);
            }
        }

        // Special Tool
        JsonArray tools = jsonObject.get("specialtool").getAsJsonArray();
        for (JsonElement tool : tools) {
            detailedJob.addSpecialTool(jsonDeserializationContext.deserialize(tool, String.class));
        }

        return detailedJob;
    }


}
