package ru.greenavto.daf.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.greenavto.daf.dto.*;
import ru.greenavto.daf.exception.DafException;
import ru.greenavto.daf.exception.ErrorCode;
import ru.greenavto.daf.gson.deserializer.component.ComponentDeserializer;
import ru.greenavto.daf.gson.deserializer.detailedjob.DetailedJobDeserializer;
import ru.greenavto.daf.gson.deserializer.detailedjob.PartConsumptionDeserializer;
import ru.greenavto.daf.gson.deserializer.job.JobDeserializer;
import ru.greenavto.daf.gson.deserializer.maingroup.MainGroupDeserializer;
import ru.greenavto.daf.gson.deserializer.vehicle.AttributeDeserializer;
import ru.greenavto.daf.gson.deserializer.vehicle.AttributeGroupDeserializer;
import ru.greenavto.daf.gson.deserializer.vehicle.DescriptionDeserializer;
import ru.greenavto.daf.gson.deserializer.vehicle.VehicleDeserializer;
import ru.greenavto.daf.gson.deserializer.vin.VinDeserializer;
import ru.greenavto.daf.model.*;
import ru.greenavto.daf.model.detailedjob.DetailedJob;
import ru.greenavto.daf.model.detailedjob.PartConsumption;
import ru.greenavto.daf.model.vehicle.Attribute;
import ru.greenavto.daf.model.vehicle.AttributeGroup;
import ru.greenavto.daf.model.vehicle.Description;
import ru.greenavto.daf.model.vehicle.Vehicle;
import ru.greenavto.daf.util.PropertiesReader;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class CatalogDaoImpl implements CatalogDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CatalogDaoImpl.class);
    private static final String HOST_HEADER = "Host";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String USER_AGENT = "User-Agent";

    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(Attribute.class, new AttributeDeserializer())
            .registerTypeAdapter(Description.class, new DescriptionDeserializer())
            .registerTypeAdapter(AttributeGroup.class, new AttributeGroupDeserializer())
            .registerTypeAdapter(Vehicle.class, new VehicleDeserializer())
            .registerTypeAdapter(MainGroup.class, new MainGroupDeserializer())
            .registerTypeAdapter(Vin.class, new VinDeserializer())
            .registerTypeAdapter(Component.class, new ComponentDeserializer())
            .registerTypeAdapter(Job.class, new JobDeserializer())
            .registerTypeAdapter(PartConsumption.class, new PartConsumptionDeserializer())
            .registerTypeAdapter(DetailedJob.class, new DetailedJobDeserializer())
            .create();

    private static String site = PropertiesReader.getSite();
    private static String urlCheckVin = PropertiesReader.getUrlCheckVin();
    private static String urlVehicle = PropertiesReader.getUrlVehicle();
    private static String urlMainGroups = PropertiesReader.getUrlMainGroups();
    private static String urlComponents = PropertiesReader.getUrlComponents();
    private static String urlJobs = PropertiesReader.getUrlJobs();
    private static String[] loginUrls = PropertiesReader.getUrlLogins();
    private static String language = "RU";

    // Create a local instance of cookie store
    private BasicCookieStore cookieStore = new BasicCookieStore();
    private HttpClientContext context = HttpClientContext.create();
    // TODO should I close httpClient?
    private CloseableHttpClient httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();

    public CatalogDaoImpl() {
        context.setCookieStore(cookieStore);
    }

    @Override
    public int login(LoginInfo loginInfo) throws DafException {
        LOGGER.debug("Performing login()...");
        int status;

        // set up POST request with username and password in request body
        HttpPost httpPost = new HttpPost(site);
        List<NameValuePair> formParams = new ArrayList<>();
        formParams.add(new BasicNameValuePair("username", loginInfo.getUsername()));
        formParams.add(new BasicNameValuePair("password", loginInfo.getPassword()));
        String curl = "Z2F";
        formParams.add(new BasicNameValuePair("curl", curl));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(formParams));
        } catch (UnsupportedEncodingException e) {
            LOGGER.info("Wrong request body encoding {}", e.getMessage());
            DafException dex = new DafException(ErrorCode.WRONG_ENCODING);
            dex.initCause(e);
            throw dex;
        }

        // make POST request with username and password
        try (CloseableHttpResponse response = httpClient.execute(httpPost, context)) {
            LOGGER.debug("Recieved response: {}", response.getStatusLine());
            status = response.getStatusLine().getStatusCode();
        } catch (IOException e) {
            LOGGER.info("Error while login on site: {}", e.getMessage());
            DafException dex = new DafException(ErrorCode.NO_LOGIN, e.getMessage());
            dex.initCause(e);
            throw dex;
        }

        // collect more cookies by performing GETs
        for (String loginUrl : loginUrls) {
            LOGGER.debug("Perform GET request {}", loginUrl);
            HttpGet httpGet = new HttpGet(loginUrl);
            setHeadersOnGetRequest(httpGet);
            try (CloseableHttpResponse response = httpClient.execute(httpGet, context)) {
                LOGGER.debug("Recieved response: {}", response.getStatusLine());
            } catch (IOException e) {
                LOGGER.info("Error while login on site: {}", e.getMessage());
                DafException dex = new DafException(ErrorCode.NO_LOGIN, e.getMessage());
                dex.initCause(e);
                throw dex;
            }
        }
        return status;
    }

    @Override
    public Vin getVin(VinRequestDto vinRequestDto) throws DafException {
        LOGGER.debug("getVin() started. parameter: {}", vinRequestDto.getCode());

        Map<String, String> params = new HashMap<>();
        params.put("inputText", vinRequestDto.getCode());

        HttpGet httpGet = setUpGetRequest(urlCheckVin, params);
        setHeadersOnGetRequest(httpGet);

        String result = performGetRequest(httpGet);
        return gson.fromJson(result, Vin.class);
    }

    @Override
    public Vehicle getVehicle(VehicleRequestDto vehicleRequestDto) throws DafException {
        LOGGER.debug("getVehicle started. Parameter {}", vehicleRequestDto.getSearchedVin());

        Map<String, String> params = new HashMap<>();
        params.put("searchedVin", vehicleRequestDto.getSearchedVin());
        params.put("languageCode", language);

        HttpGet httpGet = setUpGetRequest(urlVehicle, params);
        setHeadersOnGetRequest(httpGet);

        String result = performGetRequest(httpGet);
        return gson.fromJson(result, Vehicle.class);
    }

    @Override
    public Collection<MainGroup> getMainGroupsCollection(MainGroupRequestDto mainGroupRequestDto) throws DafException {
        LOGGER.debug("getMainGroupsCollection started. Parameter {}", mainGroupRequestDto.getSearchedVin());

        Map<String, String> params = new HashMap<>();
        params.put("searchedVin", mainGroupRequestDto.getSearchedVin());
        params.put("languageCode", language);

        HttpGet httpGet = setUpGetRequest(urlMainGroups, params);
        setHeadersOnGetRequest(httpGet);

        String result = performGetRequest(httpGet);
        Type collectionType = new TypeToken<Collection<MainGroup>>() {
        }.getType();
        return gson.fromJson(result, collectionType);
    }

    @Override
    public Collection<Component> getComponentsCollection(ComponentRequestDto componentRequestDto) throws DafException {
        LOGGER.debug("getComponentsCollection started. Parameters: {}, {}, {}", componentRequestDto.getSearchedVin(), language, componentRequestDto.getMainGroupId());

        Map<String, String> params = new HashMap<>();
        params.put("searchedVin", componentRequestDto.getSearchedVin());
        params.put("languageCode", language);
        params.put("mainGroupId", componentRequestDto.getMainGroupId());

        HttpGet httpGet = setUpGetRequest(urlComponents, params);
        setHeadersOnGetRequest(httpGet);

        String result = performGetRequest(httpGet);
        Type collectionType = new TypeToken<Collection<Component>>() {
        }.getType();
        return gson.fromJson(result, collectionType);

    }

    @Override
    public Collection<Job> getJobsCollection(JobRequestDto jobRequestDto) throws DafException {
        LOGGER.debug("getJobs() started. Parameters: {}, {}, {}, {}, {}", jobRequestDto.getVin()
                , jobRequestDto.getCompNumber()
                , jobRequestDto.getCompNumberVersion()
                , jobRequestDto.getComponentGroup()
                , jobRequestDto.getDrawingIndex());

        Map<String, String> params = new HashMap<>();
        params.put("vin", jobRequestDto.getVin());
        params.put("compNumber", jobRequestDto.getCompNumber());
        params.put("compNumberVersion", jobRequestDto.getCompNumberVersion());
        params.put("componentGroup", jobRequestDto.getComponentGroup());
        params.put("languageCode", language);
        params.put("drawingIndex", jobRequestDto.getDrawingIndex());
        params.put("isLDrawing", "undefined");

        HttpGet httpGet = setUpGetRequest(urlJobs, params);
        setHeadersOnGetRequest(httpGet);

        String result = performGetRequest(httpGet);
        Type collectionType = new TypeToken<Collection<Job>>() {
        }.getType();

        return gson.fromJson(result, collectionType);
    }

    @Override
    public Collection<DetailedJob> getDetailedJobsCollection(DetailedJobRequestDto detailedJobRequestDto) throws DafException {
        LOGGER.debug("getDetailedJobs started. Parameters: {}, {}", detailedJobRequestDto.getVin(), detailedJobRequestDto.getJobId());

        Map<String, String> params = new HashMap<>();
        params.put("vin", detailedJobRequestDto.getVin());
        params.put("languageCode", language);
        params.put("jobId", detailedJobRequestDto.getJobId());

        HttpGet httpGet = setUpGetRequest(PropertiesReader.getUrlDetailedJobs(), params);
        setHeadersOnGetRequest(httpGet);

        String result = performGetRequest(httpGet);
        Type collectionType = new TypeToken<Collection<DetailedJob>>() {
        }.getType();
        return gson.fromJson(result, collectionType);
    }

    private String performGetRequest(HttpGet httpGet) throws DafException {
        try (CloseableHttpResponse response = httpClient.execute(httpGet, context)) {
            HttpEntity entity = response.getEntity();
            if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                throw new DafException(ErrorCode.BAD_RESPONSE, String.valueOf(response.getStatusLine().getStatusCode()));
            }
            return EntityUtils.toString(entity);
        } catch (IOException e) {
            LOGGER.info("{}", e.getMessage());
            DafException dex = new DafException(ErrorCode.EXECUTE_REQUEST_ERROR);
            dex.initCause(e);
            throw dex;
        }
    }

    private void setHeadersOnGetRequest(HttpGet httpGet) {
        String host = "eportal.daf.com";
        String contentType = "application/x-www-form-urlencoded";
        String userAgent = "Mozilla/5.0 (Windows; U; MSIE 9.0; Windows NT 9.0; en-US)";
        httpGet.setHeader(HOST_HEADER, host);
        httpGet.setHeader(CONTENT_TYPE, contentType);
        httpGet.setHeader(USER_AGENT, userAgent);
    }

    private HttpGet setUpGetRequest(String url, Map<String, String> paramMap) throws DafException {
        LOGGER.debug("setUpGetRequest parameter url: {}", url);
        try {
            URIBuilder builder = new URIBuilder(url);
            for (Map.Entry<String, String> param : paramMap.entrySet()) {
//                LOGGER.debug("setUpGetRequest param map key : value, {} : {}", param.getKey(), param.getValue());
                builder.setParameter(param.getKey(), param.getValue());
            }
            URI uri = builder.build();
            LOGGER.debug("URL: {}", uri);
            return new HttpGet(uri);
        } catch (URISyntaxException e) {
            LOGGER.info("URI syntax exception, {}", e.getMessage());
            DafException dex = new DafException(ErrorCode.WRONG_URI_SYNTAX, url);
            dex.initCause(e);
            throw dex;
        }
    }


}
