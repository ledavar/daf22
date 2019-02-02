package ru.greenavto.daf.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.greenavto.daf.dao.CatalogDaoImpl;
import ru.greenavto.daf.exception.DafException;
import ru.greenavto.daf.gson.error.ErrorResponse;
import ru.greenavto.daf.model.*;
import ru.greenavto.daf.service.DafService;
import ru.greenavto.daf.service.DafServiceImpl;

public class DafEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(DafEndpoint.class);

    private DafService dafService;
    private Gson gson;

    public DafEndpoint() {
        this.dafService = new DafServiceImpl(new CatalogDaoImpl());
        gson = new GsonBuilder().setPrettyPrinting().create();
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

    public String getVehicle(String request) {
        try {
            return gson.toJson(dafService.getVehicle(request));
        } catch (DafException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return gson.toJson(errorResponse);
        }
    }

    public String getComponentsCollection(Vin vin, MainGroup mainGroup) {
        try {
            return gson.toJson(dafService.getComponentsCollection(vin, mainGroup));
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

    public String getDetailedJob(Vin vin, Job job) {
        try {
            return gson.toJson(dafService.getDetailedJobCollection(vin, job));
        } catch (DafException e) {
            LOGGER.info("{}", e.getMessage());
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return gson.toJson(errorResponse);
        }
    }


}
