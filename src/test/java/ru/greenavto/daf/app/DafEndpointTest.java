package ru.greenavto.daf.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.greenavto.daf.exception.DafException;
import ru.greenavto.daf.model.Component;
import ru.greenavto.daf.model.Job;
import ru.greenavto.daf.model.MainGroup;
import ru.greenavto.daf.model.Vin;
import ru.greenavto.daf.model.detailedjob.DetailedJob;
import ru.greenavto.daf.util.DafFileWriter;
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
    private static final String WRONG = "wrong";

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
        if (SUCCESS.equals(dafEndpoint.login(PropertiesReader.getUserName(), PropertiesReader.getPassword()))) {
            String vinString = dafEndpoint.getVin(WRONG);
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
        if (SUCCESS.equals(dafEndpoint.login(PropertiesReader.getUserName(), PropertiesReader.getPassword()))) {
            // get Vehicle by non-existent vin
            String vehicleString = dafEndpoint.getVehicle(WRONG);
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
            String groupsString = dafEndpoint.getMainGroups(WRONG);
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

    @Test
    public void testComponentsByWrongMainGroupAndWrongVin() {
        if (SUCCESS.equals(dafEndpoint.login(PropertiesReader.getUserName(), PropertiesReader.getPassword()))) {
            Vin vin = new Vin(WRONG, WRONG);
            MainGroup mainGroup = new MainGroup(WRONG, WRONG);

            String componentsString = dafEndpoint.getComponentsCollection(vin, mainGroup);

            Type collectionType = new TypeToken<Collection<Component>>() {
            }.getType();
            Collection<Component> componentCollection = gson.fromJson(componentsString, collectionType);
            assertTrue(componentCollection.size() == 0);
        }
    }

    @Test
    public void testComponentsByWrongMainGroupAndCorrectVin() {
        if (SUCCESS.equals(dafEndpoint.login(PropertiesReader.getUserName(), PropertiesReader.getPassword()))) {
            // get correct Vin
            String vinString = dafEndpoint.getVin(code);
            assertTrue(vinString.contains("OKVIN"));
            Vin vin = gson.fromJson(vinString, Vin.class);

            MainGroup mainGroup = new MainGroup(WRONG, WRONG);

            String componentsString = dafEndpoint.getComponentsCollection(vin, mainGroup);

            Type collectionType = new TypeToken<Collection<Component>>() {
            }.getType();
            Collection<Component> componentCollection = gson.fromJson(componentsString, collectionType);
            assertTrue(componentCollection.size() == 0);
        }
    }

    @Test
    public void testComponentsByCorrectMainGroupAndWrongVin() {
        if (SUCCESS.equals(dafEndpoint.login(PropertiesReader.getUserName(), PropertiesReader.getPassword()))) {
            // get correct Vin
            String vinString = dafEndpoint.getVin(code);
            assertTrue(vinString.contains("OKVIN"));
            Vin vinCorrect = gson.fromJson(vinString, Vin.class);
            Vin vinWrong = new Vin(WRONG, WRONG);

            // get main groups by vin
            String groupsString = dafEndpoint.getMainGroups(vinCorrect.getWmi());
            assertTrue(groupsString.contains("0100"));
            assertTrue(groupsString.contains("0200"));

            Type collectionType;
            collectionType = new TypeToken<Collection<MainGroup>>() {
            }.getType();
            Collection<MainGroup> mainGroupCollection = gson.fromJson(groupsString, collectionType);
            assertTrue(mainGroupCollection.size() != 0);

            // get first correct main group from collection
            MainGroup mainGroupCorrect = new ArrayList<>(mainGroupCollection).get(0);

            String componentsString = dafEndpoint.getComponentsCollection(vinWrong, mainGroupCorrect);

            collectionType = new TypeToken<Collection<Component>>() {
            }.getType();
            Collection<Component> componentCollection = gson.fromJson(componentsString, collectionType);
            assertTrue(componentCollection.size() == 0);
        }
    }

    @Test
    public void testJobs() {
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

            // get Jobs by collection[0] Components
            Component component = new ArrayList<>(componentCollection).get(0);
            String jobsString = dafEndpoint.getJobsCollection(vin, component);
            LOGGER.debug("{}", jobsString);
        }
    }

    @Test
    public void testDetailedJobs() {
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

            // get Jobs by collection[0] Components
            Component component = new ArrayList<>(componentCollection).get(0);
            String jobsString = dafEndpoint.getJobsCollection(vin, component);
            collectionType = new TypeToken<Collection<Job>>() {
            }.getType();
            Collection<Job> jobCollection = gson.fromJson(jobsString, collectionType);
            assertTrue(jobCollection.size() != 0);


            //get DetailedJob by collection[0] Job
            Job job = new ArrayList<>(jobCollection).get(0);
            LOGGER.debug("JOB {}", job);
            String detailedJobString = dafEndpoint.getDetailedJob(vin, job);
            LOGGER.debug("detailedJobString {}", detailedJobString);
        }
    }

    @Test
    public void testOneDetailedJob() {
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

            // get Jobs by collection[0] Components
            Component component = new ArrayList<>(componentCollection).get(0);
            String jobsString = dafEndpoint.getJobsCollection(vin, component);

            collectionType = new TypeToken<Collection<Job>>() {
            }.getType();
            Collection<Job> jobsCollection = gson.fromJson(jobsString, collectionType);
            assertTrue(jobsCollection.size() != 0);

            // get details of job = jobsCollection[0]
            //Job job = new ArrayList<>(jobsCollection).get(0);
            //String detailedJobs = dafEndpoint.getDetailedJob(vin, job);
            //LOGGER.debug("Detailed job: {}", detailedJobs);


            for (Job job : jobsCollection) {
                String detailedJobs = dafEndpoint.getDetailedJob(vin, job);
                LOGGER.debug("Detailed job: {}", detailedJobs);
            }

        }
    }

    @Test
    public void testAllDetailedJobs() {
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

            // get all Components for all MainGroups
            Collection<Component> componentCollection = new ArrayList<>();
            for (MainGroup mainGroup : mainGroupCollection) {
                String componentsString = dafEndpoint.getComponentsCollection(vin, mainGroup);
                collectionType = new TypeToken<Collection<Component>>() {
                }.getType();
                Collection<Component> components = gson.fromJson(componentsString, collectionType);
                componentCollection.addAll(components);
            }

            // get all jobs by all Components
            Collection<Job> jobCollection = new ArrayList<>();
            for (Component component : componentCollection) {
                String jobsString = dafEndpoint.getJobsCollection(vin, component);
                collectionType = new TypeToken<Collection<Job>>() {
                }.getType();
                Collection<Job> jobs = gson.fromJson(jobsString, collectionType);
                jobCollection.addAll(jobs);
            }

            // get all detailed jobs one by one
            Collection<DetailedJob> detailedJobCollection = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            for (Job job : jobCollection) {
                String detailedJobs = dafEndpoint.getDetailedJob(vin, job);
                sb.append(detailedJobs);
                LOGGER.debug("Detailed job: {}", detailedJobs);
            }

            DafFileWriter.write(sb.toString());

        }
    }

}
