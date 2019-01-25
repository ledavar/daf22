package ru.greenavto.daf.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.greenavto.daf.dao.CatalogDao;
import ru.greenavto.daf.dto.*;
import ru.greenavto.daf.exception.DafException;
import ru.greenavto.daf.exception.ErrorCode;
import ru.greenavto.daf.model.*;
import ru.greenavto.daf.model.vehicle.Vehicle;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

public class DafServiceImpl implements DafService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DafServiceImpl.class);
    private static final String OK = "OKVIN";

    private CatalogDao catalogDao;

    public DafServiceImpl(CatalogDao catalogDao) {
        this.catalogDao = catalogDao;
    }

    @Override
    public void login(LoginInfo loginInfo) throws DafException {
        try {
            catalogDao.login(loginInfo);
        } catch (DafException e) {
            LOGGER.info("Error while login {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public Vin getVin(String request) throws IOException, URISyntaxException, DafException {
        VinRequestDto vinRequestDto = new VinRequestDto(request);
        Vin vin = catalogDao.getVin(vinRequestDto);
        if (!OK.equals(vin.getResult())) {
            throw new DafException(ErrorCode.WRONG_VIN, request);
        }
        return vin;
    }

    @Override
    public Vehicle getVehicle(String request) throws IOException, URISyntaxException {
        VehicleRequestDto vehicleRequestDto = new VehicleRequestDto(request);
        return catalogDao.getVehicle(vehicleRequestDto);
    }

    @Override
    public Collection<MainGroup> getMainGroupsCollection(String request) throws IOException, URISyntaxException {
        MainGroupRequestDto mainGroupRequestDto = new MainGroupRequestDto(request);
        return catalogDao.getMainGroupsCollection(mainGroupRequestDto);
    }

    @Override
    public Collection<Component> getComponentsCollection(String wmi, String id) throws IOException, URISyntaxException {
        ComponentRequestDto componentRequestDto = new ComponentRequestDto(wmi, id);
        return catalogDao.getComponentsCollection(componentRequestDto);
    }

    @Override
    public Collection<Job> getJobsCollection(Vin vin, Component component) throws IOException, URISyntaxException {
        JobRequestDto jobRequestDto = new JobRequestDto();
        jobRequestDto.setVin(vin.getWmi());
        jobRequestDto.setCompNumber(component.getComponentReference());
        jobRequestDto.setCompNumberVersion(component.getVersion());
        jobRequestDto.setComponentGroup(component.getId());
        jobRequestDto.setDrawingIndex(component.getIndex());

        return catalogDao.getJobsCollection(jobRequestDto);
    }

    @Override
    public Collection<MainGroup> getCatalog(String request) throws IOException, URISyntaxException, DafException {
        // get VIN number
        Vin vin = getVin(request);

        // get Main Groups
        MainGroupRequestDto mainGroupRequestDto = new MainGroupRequestDto(vin.getWmi());
        Collection<MainGroup> mainGroupCollection = catalogDao.getMainGroupsCollection(mainGroupRequestDto);

        // get Components and add them to the appropriate Main Group
         for (MainGroup mainGroup : mainGroupCollection) {
             ComponentRequestDto componentRequestDto = new ComponentRequestDto(vin.getWmi(), mainGroup.getId());
             Collection<Component> componentCollection = catalogDao.getComponentsCollection(componentRequestDto);
             mainGroup.setComponentList(new ArrayList<>(componentCollection));
         }

        return mainGroupCollection;
    }
}
