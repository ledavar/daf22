package ru.greenavto.daf.service;

import ru.greenavto.daf.exception.DafException;
import ru.greenavto.daf.model.*;
import ru.greenavto.daf.model.vehicle.Vehicle;

import java.util.Collection;

public interface DafService {

    // main method that will collect all data in one??
    Collection<MainGroup> getCatalog(String request) throws DafException;

    void login(LoginInfo loginInfo) throws DafException;

    Vin getVin(String request) throws DafException;

    Vehicle getVehicle(String request) throws DafException;

    Collection<MainGroup> getMainGroupsCollection(String request) throws DafException;

    Collection<Component> getComponentsCollection(Vin vin, MainGroup mainGroup) throws DafException;

    Collection<Job> getJobsCollection(Vin vin, Component component) throws DafException;


    // TEST
    String getDetailedJob(Vin vin, Job job) throws DafException;

}
