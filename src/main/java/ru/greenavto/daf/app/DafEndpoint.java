package ru.greenavto.daf.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.greenavto.daf.dao.CatalogDaoImpl;
import ru.greenavto.daf.exception.DafException;
import ru.greenavto.daf.gson.error.ErrorResponse;
import ru.greenavto.daf.gson.serializer.vehicle.AttributeSerializer;
import ru.greenavto.daf.gson.serializer.vehicle.VehicleSerializer;
import ru.greenavto.daf.gson.serializer.vin.VinSerializer;
import ru.greenavto.daf.model.Component;
import ru.greenavto.daf.model.LoginInfo;
import ru.greenavto.daf.model.Vin;
import ru.greenavto.daf.model.vehicle.Attribute;
import ru.greenavto.daf.model.vehicle.Vehicle;
import ru.greenavto.daf.service.DafService;
import ru.greenavto.daf.service.DafServiceImpl;

public class DafEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(DafEndpoint.class);

    private DafService dafService;
    private Gson gson;

    public DafEndpoint() {
        this.dafService = new DafServiceImpl(new CatalogDaoImpl());
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Attribute.class, new AttributeSerializer())
                .registerTypeAdapter(Vehicle.class, new VehicleSerializer())
                .registerTypeAdapter(Vin.class, new VinSerializer())
                .create();
    }

    public String login(String userName, String password) {
        try {
            dafService.login(new LoginInfo(userName, password));
        } catch (DafException dex) {
            ErrorResponse errorResponse = new ErrorResponse(dex.getMessage());
            return gson.toJson(errorResponse);
        }
        return "OK";
    }

    public String getVin(String request) {
        try {
            return gson.toJson(dafService.getVin(request));
        } catch (DafException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return gson.toJson(errorResponse);
        }
    }

    public String getMainGroups(String request) {
        try {
            return gson.toJson(dafService.getMainGroupsCollection(request));
        } catch (DafException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return gson.toJson(errorResponse);
        }
    }

    public String getVehicle(String request) throws DafException {
        return gson.toJson(dafService.getVehicle(request));
    }

    public String getComponentsCollection(String wmi, String id) {
        try {
            return gson.toJson(dafService.getComponentsCollection(wmi, id));
        } catch (DafException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return gson.toJson(errorResponse);
        }
    }

    public String getJobsCollection(Vin vin, Component component) {
        try {
            return gson.toJson(dafService.getJobsCollection(vin, component));
        } catch (DafException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return gson.toJson(errorResponse);
        }
    }

    public String getCatalog(String request) {
        try {
            return gson.toJson(dafService.getCatalog(request));
        } catch (DafException e) {
            LOGGER.info("{}", e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return gson.toJson(errorResponse);
        }
    }
}
