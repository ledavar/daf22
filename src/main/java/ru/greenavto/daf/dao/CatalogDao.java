package ru.greenavto.daf.dao;

import ru.greenavto.daf.dto.*;
import ru.greenavto.daf.exception.DafException;
import ru.greenavto.daf.model.*;
import ru.greenavto.daf.model.detailedjob.DetailedJob;
import ru.greenavto.daf.model.vehicle.Vehicle;

import java.util.Collection;

public interface CatalogDao {

    int login(LoginInfo loginInfo) throws DafException;

    Vin getVin(VinRequestDto vinRequestDto) throws DafException;

    Vehicle getVehicle(VehicleRequestDto vehicleRequestDto) throws DafException;

    Collection<MainGroup> getMainGroupsCollection(MainGroupRequestDto mainGroupRequestDto) throws DafException;

    Collection<Component> getComponentsCollection(ComponentRequestDto componentRequestDto) throws DafException;

    Collection<Job> getJobsCollection(JobRequestDto jobRequestDto) throws DafException;

    Collection<DetailedJob> getDetailedJobsCollection(DetailedJobRequestDto detailedJobRequestDto) throws DafException;

}
