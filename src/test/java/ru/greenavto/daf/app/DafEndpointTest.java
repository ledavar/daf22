package ru.greenavto.daf.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.greenavto.daf.exception.DafException;
import ru.greenavto.daf.model.Component;
import ru.greenavto.daf.model.MainGroup;
import ru.greenavto.daf.model.Vin;
import ru.greenavto.daf.util.PropertiesReader;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;

public class DafEndpointTest {

    private static DafEndpoint dafEndpoint = new DafEndpoint();
    private static final Logger LOGGER = LoggerFactory.getLogger(DafEndpointTest.class);

    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /*@BeforeClass
    public static void login() {
        String username = PropertiesReader.getUserName();
        String password = PropertiesReader.getPassword();
        dafEndpoint.login(username, password);
    }

    @Test
    public void testVin() throws IOException, URISyntaxException {
        String code = "0G124720";
        System.out.println(dafEndpoint.getVin(code));
    }

    @Test
    public void testVehicle() throws IOException, URISyntaxException, DafException {
        String code = "0G124720";
        System.out.println(dafEndpoint.getVehicle("XLRTEH4300G124720"));
    }

    @Test
    public void testMainGroups() throws IOException, URISyntaxException {
        String code = "0G124720";
        String vinString = dafEndpoint.getVin(code);
        Vin vin = gson.fromJson(vinString, Vin.class);
        System.out.println(dafEndpoint.getMainGroups(vin.getWmi()));
    }

    @Test
    public void testComponents() throws IOException, URISyntaxException, DafException {
        String code = "0g124720";
        String vinString = dafEndpoint.getVin(code);
        LOGGER.debug("testComponents(), vinString: {}", vinString);
        Vin vin = gson.fromJson(vinString, Vin.class);
        LOGGER.debug("testComponents(), vin object: {}", vin.toString());
        String mainGroupsString = dafEndpoint.getMainGroups(vin.getWmi());
        //LOGGER.debug("mainGroupsString {}", mainGroupsString);

        Type collectionType = new TypeToken<Collection<MainGroup>>() {
        }.getType();
        Collection<MainGroup> collection = gson.fromJson(mainGroupsString, collectionType);
        //LOGGER.debug(" collection {}", collection);

        // get components
        Collection<Component> components = new ArrayList<>();
        for (MainGroup group : collection) {
            components.addAll(dafEndpoint.getComponentsCollection(vin.getWmi(), group.getId()));
        }
        LOGGER.debug("Components: {}", components);
    }

    @Test
    public void testGetJobsCollection() throws IOException, URISyntaxException {

        String code = "0g124720";
        String vinString = dafEndpoint.getVin(code);
        LOGGER.debug("testComponents(), vinString: {}", vinString);
        Vin vin = gson.fromJson(vinString, Vin.class);
        LOGGER.debug("testComponents(), vin object: {}", vin.toString());
        String mainGroupsString = dafEndpoint.getMainGroups(vin.getWmi());
        //LOGGER.debug("mainGroupsString {}", mainGroupsString);

        Type collectionType = new TypeToken<Collection<MainGroup>>() {
        }.getType();
        Collection<MainGroup> collection = gson.fromJson(mainGroupsString, collectionType);
        //LOGGER.debug(" collection {}", collection);

        // get components
        Collection<Component> components = new ArrayList<>();
        for (MainGroup group : collection) {
            components.addAll(dafEndpoint.getComponentsCollection(vin.getWmi(), group.getId()));
        }
        LOGGER.debug("Components: {}", components);

        // get jobs
        for (Component component : components) {
            LOGGER.debug("JOB: {}", gson.toJson(dafEndpoint.getJobsCollection(vin, component)));
        }
        // iterate only one component
        //LOGGER.debug("JOB: {}", dafEndpoint.getJobsCollection(vin, components.iterator().next()));

    }

    @Test
    public void testCatalog() throws IOException, DafException, URISyntaxException {
        String code = "0g124720";
        LOGGER.debug("Trying to get Main Groups with List of Components: {}", dafEndpoint.getCatalog(code));
    }*/


    @Test
    public void testLogin() {
        String username = PropertiesReader.getUserName();
        String password = PropertiesReader.getPassword();
        assertEquals("OK", dafEndpoint.login(username, password));
    }


}
