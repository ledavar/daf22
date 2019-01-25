package ru.greenavto.daf.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.greenavto.daf.exception.DafException;
import ru.greenavto.daf.exception.ErrorCode;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    private static Properties prop = new Properties();
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesReader.class);

    static {
        LOGGER.debug("Initializing static block of PropertiesReader...");
        InputStream input = null;

        try {
            String filename = "application.properties";
            input = PropertiesReader.class.getClassLoader().getResourceAsStream(filename);
            if (input == null) {
                LOGGER.warn("Cant read application.properties file {}", filename);
                throw new DafException(ErrorCode.NO_PROPERTIES, filename);
            }
            prop.load(input);
        } catch (IOException | DafException ex) {
            LOGGER.warn("Error in static block {}", ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private PropertiesReader() throws DafException {
    }


    public static String getUserName() {return prop.getProperty("username");}

    public static String getPassword() {return prop.getProperty("password");}

    public static String getSite() {return prop.getProperty("site");}

    public static String getUrlCheckVin() {return prop.getProperty("urlCheckVin");}

    public static String getUrlVehicle() {return prop.getProperty("urlVehicle");}

    public static String getUrlMainGroups() {return prop.getProperty("urlMainGroups");}

    public static String getUrlComponents() {return prop.getProperty("urlComponents");}

    public static String getUrlJobs() {return prop.getProperty("urlJobs");}

    // here goes KOSTYL

    public static String[] getUrlLogins() {
        int lastUrlLoginIndex = 11;
        String[] urlLogins = new String[lastUrlLoginIndex + 1];
        for (int index = 0; index <= lastUrlLoginIndex; index++) {
            String propertyName = "urlLogin" + index;
            urlLogins[index] = prop.getProperty(propertyName);
        }
        return urlLogins;
    }


}
