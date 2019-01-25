package ru.greenavto.daf.service;

import ru.greenavto.daf.dto.JobRequestDto;
import ru.greenavto.daf.exception.DafException;
import ru.greenavto.daf.model.*;
import ru.greenavto.daf.model.vehicle.Vehicle;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

public interface DafService {

    // main method that will collect all data in one??
    Collection<MainGroup> getCatalog(String request) throws IOException, URISyntaxException, DafException;

    void login(LoginInfo loginInfo) throws DafException;

    Vin getVin(String request) throws IOException, URISyntaxException, DafException;

    Vehicle getVehicle(String request) throws IOException, URISyntaxException;

    Collection<MainGroup> getMainGroupsCollection(String request) throws IOException, URISyntaxException;

    Collection<Component> getComponentsCollection(String wmi, String id) throws IOException, URISyntaxException;

    Collection<Job> getJobsCollection(Vin vin, Component component) throws IOException, URISyntaxException;

}
