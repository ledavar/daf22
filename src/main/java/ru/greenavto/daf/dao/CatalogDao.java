package ru.greenavto.daf.dao;

import ru.greenavto.daf.dto.*;
import ru.greenavto.daf.exception.DafException;
import ru.greenavto.daf.model.*;
import ru.greenavto.daf.model.vehicle.Vehicle;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

public interface CatalogDao {

    int login(LoginInfo loginInfo) throws DafException;

    Vin getVin(VinRequestDto vinRequestDto) throws IOException, URISyntaxException;

    Vehicle getVehicle(VehicleRequestDto vehicleRequestDto) throws URISyntaxException, IOException;

    Collection<MainGroup> getMainGroupsCollection(MainGroupRequestDto mainGroupRequestDto) throws URISyntaxException, IOException;

    Collection<Component> getComponentsCollection(ComponentRequestDto componentRequestDto) throws URISyntaxException, IOException;

    Collection<Job> getJobsCollection(JobRequestDto jobRequestDto) throws URISyntaxException, IOException;

}
