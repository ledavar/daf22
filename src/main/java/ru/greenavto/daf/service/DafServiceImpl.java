package ru.greenavto.daf.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.greenavto.daf.dao.CatalogDao;
import ru.greenavto.daf.dto.*;
import ru.greenavto.daf.exception.DafException;
import ru.greenavto.daf.exception.ErrorCode;
import ru.greenavto.daf.model.*;
import ru.greenavto.daf.model.vehicle.Vehicle;

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
        catalogDao.login(loginInfo);
    }

    @Override
    public Vin getVin(String request) throws DafException {
        VinRequestDto vinRequestDto = new VinRequestDto(request);
        Vin vin = catalogDao.getVin(vinRequestDto);
        if (!OK.equals(vin.getResult())) {
            throw new DafException(ErrorCode.WRONG_VIN, request);
        }
        return vin;
    }

    @Override
    public Vehicle getVehicle(String request) throws DafException {
        VehicleRequestDto vehicleRequestDto = new VehicleRequestDto(request);
        return catalogDao.getVehicle(vehicleRequestDto);
    }

    @Override
    public Collection<MainGroup> getMainGroupsCollection(String request) throws DafException {
        MainGroupRequestDto mainGroupRequestDto = new MainGroupRequestDto(request);
        return catalogDao.getMainGroupsCollection(mainGroupRequestDto);
    }

    @Override
    public Collection<Component> getComponentsCollection(Vin vin, MainGroup mainGroup) throws DafException {
        ComponentRequestDto componentRequestDto = new ComponentRequestDto(vin.getWmi(), mainGroup.getId());
        return catalogDao.getComponentsCollection(componentRequestDto);
    }

    @Override
    public Collection<Job> getJobsCollection(Vin vin, Component component) throws DafException {
        JobRequestDto jobRequestDto = new JobRequestDto();
        jobRequestDto.setVin(vin.getWmi());
        jobRequestDto.setCompNumber(component.getComponentReference());
        jobRequestDto.setCompNumberVersion(component.getVersion());
        jobRequestDto.setComponentGroup(component.getId());
        jobRequestDto.setDrawingIndex(component.getIndex());
        return catalogDao.getJobsCollection(jobRequestDto);
    }

    @Override
    public Collection<MainGroup> getCatalog(String request) throws DafException {
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
