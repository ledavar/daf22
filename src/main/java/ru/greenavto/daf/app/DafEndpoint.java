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
import ru.greenavto.daf.model.Job;
import ru.greenavto.daf.model.LoginInfo;
import ru.greenavto.daf.model.Vin;
import ru.greenavto.daf.model.vehicle.Attribute;
import ru.greenavto.daf.model.vehicle.Vehicle;
import ru.greenavto.daf.service.DafService;
import ru.greenavto.daf.service.DafServiceImpl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

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

    public void login(String userName, String password) {
        try {
            dafService.login(new LoginInfo(userName, password));
        } catch (DafException e) {
            LOGGER.info("{}", e.getMessage());
            System.exit(0);
        }
    }

    public String getVin(String request) throws IOException, URISyntaxException {
        Vin vin = null;
        try {
            vin = dafService.getVin(request);
        } catch (DafException e) {
            LOGGER.info("{}", e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return gson.toJson(errorResponse);
        }
        return gson.toJson(vin);
    }

    public String getMainGroups(String request) throws IOException, URISyntaxException {
        //dafService.getMainGroupsCollection(request);
        return gson.toJson(dafService.getMainGroupsCollection(request));
    }

    public String getVehicle(String request) throws IOException, URISyntaxException {
        return gson.toJson(dafService.getVehicle(request));
    }

    public Collection<Component> getComponentsCollection(String wmi, String id) throws IOException, URISyntaxException {
        return dafService.getComponentsCollection(wmi, id);
    }

    public Collection<Job> getJobsCollection(Vin vin, Component component) throws IOException, URISyntaxException {
        return dafService.getJobsCollection(vin, component);
    }

    public String getCatalog(String request) throws DafException, IOException, URISyntaxException {
        String result;
        try {
            result = gson.toJson(dafService.getCatalog(request));
        } catch (DafException e) {
            LOGGER.info("{}", e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return gson.toJson(errorResponse);
        }
        return result;
    }
}
