package ru.greenavto.daf.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.greenavto.daf.model.Component;
import ru.greenavto.daf.model.MainGroup;
import ru.greenavto.daf.model.Vin;
import ru.greenavto.daf.util.PropertiesReader;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

public class DafEndpointTest {

    private DafEndpoint dafEndpoint = new DafEndpoint();
    private static final Logger LOGGER = LoggerFactory.getLogger(DafEndpointTest.class);
    private static final String SUCCESS = "OK";
    private static final String ERROR = "error";

    private String code = "0G124720";
    private String vehicleName = "XF_E6-XF 460 FT";
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Test
    public void testLogin() {
        String username = PropertiesReader.getUserName();
        String password = PropertiesReader.getPassword();
        assertEquals("OK", dafEndpoint.login(username, password));
    }

    @Test
    public void testVin() {
        if (SUCCESS.equals(dafEndpoint.login(PropertiesReader.getUserName(), PropertiesReader.getPassword()))) {
            String vinString = dafEndpoint.getVin(code);
            assertTrue(vinString.contains("OKVIN"));
        }
    }

    @Test
    public void testWrongVin() {
        String wrongCode = "NON_EXISTENT_CODE";
        if (SUCCESS.equals(dafEndpoint.login(PropertiesReader.getUserName(), PropertiesReader.getPassword()))) {
            String vinString = dafEndpoint.getVin(wrongCode);
            assertFalse(vinString.contains("OKVIN"));
            assertTrue(vinString.contains(ERROR));
        }
    }

    @Test
    public void testVehicle() {
        if (SUCCESS.equals(dafEndpoint.login(PropertiesReader.getUserName(), PropertiesReader.getPassword()))) {
            // get Vin
            String vinString = dafEndpoint.getVin(code);
            assertTrue(vinString.contains("OKVIN"));
            Vin vin = gson.fromJson(vinString, Vin.class);

            // get Vehicle
            String vehicleString = dafEndpoint.getVehicle(vin.getWmi());
            assertTrue(vehicleString.contains(vehicleName));
        }
    }

    @Test
    public void testNonExistentVehicle() {
        String wrongVin = "NON_EXISTENT_VIN";
        if (SUCCESS.equals(dafEndpoint.login(PropertiesReader.getUserName(), PropertiesReader.getPassword()))) {
            // get Vehicle by non-existent vin
            String vehicleString = dafEndpoint.getVehicle(wrongVin);
            assertFalse(vehicleString.contains(vehicleName));
        }
    }


    @Test
    public void testMainGroups() {
        if (SUCCESS.equals(dafEndpoint.login(PropertiesReader.getUserName(), PropertiesReader.getPassword()))) {
            // get Vin
            String vinString = dafEndpoint.getVin(code);
            assertTrue(vinString.contains("OKVIN"));
            Vin vin = gson.fromJson(vinString, Vin.class);

            // get main groups by vin
            String groupsString = dafEndpoint.getMainGroups(vin.getWmi());
            assertTrue(groupsString.contains("0100"));
            assertTrue(groupsString.contains("0200"));

            Type collectionType = new TypeToken<Collection<MainGroup>>() {
            }.getType();
            Collection<MainGroup> collection = gson.fromJson(groupsString, collectionType);
            assertTrue(collection.size() != 0);
        }
    }

    @Test
    public void testMainGroupsByWrongVin() {
        if (SUCCESS.equals(dafEndpoint.login(PropertiesReader.getUserName(), PropertiesReader.getPassword()))) {

            // get main groups by non-existent vin
            String groupsString = dafEndpoint.getMainGroups("NON_EXISTENT_VIN");
            assertFalse(groupsString.contains("0100"));
            assertFalse(groupsString.contains("0200"));

            Type collectionType = new TypeToken<Collection<MainGroup>>() {
            }.getType();
            Collection<MainGroup> collection = gson.fromJson(groupsString, collectionType);
            assertTrue(collection.size() == 0);
        }
    }

    @Test
    public void testComponents() {
        if (SUCCESS.equals(dafEndpoint.login(PropertiesReader.getUserName(), PropertiesReader.getPassword()))) {
            // get Vin
            String vinString = dafEndpoint.getVin(code);
            assertTrue(vinString.contains("OKVIN"));
            Vin vin = gson.fromJson(vinString, Vin.class);

            // get main groups by vin
            String groupsString = dafEndpoint.getMainGroups(vin.getWmi());
            assertTrue(groupsString.contains("0100"));
            assertTrue(groupsString.contains("0200"));

            Type collectionType;
            collectionType = new TypeToken<Collection<MainGroup>>() {
            }.getType();
            Collection<MainGroup> mainGroupCollection = gson.fromJson(groupsString, collectionType);
            assertTrue(mainGroupCollection.size() != 0);

            // get Components by collection[0] MainGroup
            MainGroup mainGroup = new ArrayList<>(mainGroupCollection).get(0);
            String componentsString = dafEndpoint.getComponentsCollection(vin, mainGroup);

            collectionType = new TypeToken<Collection<Component>>() {
            }.getType();
            Collection<Component> componentCollection = gson.fromJson(componentsString, collectionType);
            assertTrue(componentCollection.size() != 0);
        }
    }
}
