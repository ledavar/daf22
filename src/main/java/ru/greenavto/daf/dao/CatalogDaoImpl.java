package ru.greenavto.daf.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.http.HttpEntity;
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
import ru.greenavto.daf.gson.deserializer.job.JobDeserializer;
import ru.greenavto.daf.gson.deserializer.maingroup.MainGroupDeserializer;
import ru.greenavto.daf.gson.deserializer.vehicle.AttributeDeserializer;
import ru.greenavto.daf.gson.deserializer.vehicle.AttributeGroupDeserializer;
import ru.greenavto.daf.gson.deserializer.vehicle.DescriptionDeserializer;
import ru.greenavto.daf.gson.deserializer.vehicle.VehicleDeserializer;
import ru.greenavto.daf.gson.deserializer.vin.VinDeserializer;
import ru.greenavto.daf.model.*;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
            .create();

    private static String curl = "Z2F";
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
    public Vin getVin(VinRequestDto vinRequestDto) throws IOException, URISyntaxException {
        LOGGER.debug("getVin() started. parameter: {}", vinRequestDto.getCode());

        URIBuilder builder = new URIBuilder(urlCheckVin);
        builder.setParameter("inputText", vinRequestDto.getCode());

        LOGGER.debug("getVin() started. URL: {}", builder.build().toString());

        HttpGet httpGet = new HttpGet(builder.build());
        setHeadersOnGetRequest(httpGet);

        try (CloseableHttpResponse response = httpClient.execute(httpGet, context)) {
            LOGGER.debug("Recieved response: {}", response.getStatusLine());
            HttpEntity entity = response.getEntity();
            String responseBodyJson = EntityUtils.toString(entity);
            LOGGER.debug("Response body: {}", responseBodyJson);
            return gson.fromJson(responseBodyJson, Vin.class);
        }
    }

    @Override
    public Vehicle getVehicle(VehicleRequestDto vehicleRequestDto) throws URISyntaxException, IOException {
        LOGGER.debug("getVehicle started. Parameter {}", vehicleRequestDto.getSearchedVin());

        URIBuilder builder = new URIBuilder(urlVehicle);
        builder.setParameter("searchedVin", vehicleRequestDto.getSearchedVin()).setParameter("languageCode", language);

        LOGGER.debug("getVehicle started. URL: {}", builder.build().toString());

        HttpGet httpGet = new HttpGet(builder.build());
        setHeadersOnGetRequest(httpGet);

        try (CloseableHttpResponse response = httpClient.execute(httpGet, context)) {
            LOGGER.debug("Recieved response: {}", response.getStatusLine());
            HttpEntity entity = response.getEntity();
            String responseBodyJson = EntityUtils.toString(entity);
            return gson.fromJson(responseBodyJson, Vehicle.class);
        }
    }

    @Override
    public Collection<MainGroup> getMainGroupsCollection(MainGroupRequestDto mainGroupRequestDto) throws URISyntaxException, IOException {
        LOGGER.debug("getMainGroupsCollection started. Parameter {}", mainGroupRequestDto.getSearchedVin());

        URIBuilder builder = new URIBuilder(urlMainGroups);
        builder.setParameter("searchedVin", mainGroupRequestDto.getSearchedVin()).setParameter("languageCode", language);

        LOGGER.debug("getMainGroupsCollection started. URL: {}", builder.build().toString());

        HttpGet httpGet = new HttpGet(builder.build());
        setHeadersOnGetRequest(httpGet);

        try (CloseableHttpResponse response = httpClient.execute(httpGet, context)) {
            LOGGER.debug("Recieved response: {}", response.getStatusLine());
            HttpEntity entity = response.getEntity();
            String responseBodyJson = EntityUtils.toString(entity);
            //LOGGER.debug("Recieved response BODY {}", responseBodyJson);
            Type collectionType = new TypeToken<Collection<MainGroup>>() {
            }.getType();
            Collection<MainGroup> collection = gson.fromJson(responseBodyJson, collectionType);
            return collection;
        }
    }

    @Override
    public Collection<Component> getComponentsCollection(ComponentRequestDto componentRequestDto) throws URISyntaxException, IOException {
        LOGGER.debug("getComponentsCollection started. Parameters: {}, {}, {}", componentRequestDto.getSearchedVin(), language, componentRequestDto.getMainGroupId());

        URIBuilder builder = new URIBuilder(urlComponents);
        URI uri = builder.setParameter("searchedVin", componentRequestDto.getSearchedVin())
                .setParameter("languageCode", language)
                .setParameter("mainGroupId", componentRequestDto.getMainGroupId())
                .build();

        LOGGER.debug("getComponentsCollection URL: {}", uri.toString());

        HttpGet httpGet = new HttpGet(uri);
        setHeadersOnGetRequest(httpGet);

        try (CloseableHttpResponse response = httpClient.execute(httpGet, context)) {
            LOGGER.debug("Recieved response: {}", response.getStatusLine());
            HttpEntity entity = response.getEntity();
            String responseBodyJson = EntityUtils.toString(entity);
            //LOGGER.debug("Recieved response BODY {}", responseBodyJson);
            Type collectionType = new TypeToken<Collection<Component>>() {
            }.getType();
            Collection<Component> collection = gson.fromJson(responseBodyJson, collectionType);
            return collection;
        }
    }

    @Override
    public Collection<Job> getJobsCollection(JobRequestDto jobRequestDto) throws URISyntaxException, IOException {
        LOGGER.debug("getJobs() started. Parameters: {}, {}, {}, {}, {}", jobRequestDto.getVin()
                , jobRequestDto.getCompNumber()
                , jobRequestDto.getCompNumberVersion()
                , jobRequestDto.getComponentGroup()
                , jobRequestDto.getDrawingIndex());

        URIBuilder builder = new URIBuilder(urlJobs);
        URI uri = builder.setParameter("vin", jobRequestDto.getVin())
                .setParameter("compNumber", jobRequestDto.getCompNumber())
                .setParameter("compNumberVersion", jobRequestDto.getCompNumberVersion())
                .setParameter("componentGroup", jobRequestDto.getComponentGroup())
                .setParameter("languageCode", language)
                .setParameter("drawingIndex", jobRequestDto.getDrawingIndex())
                .setParameter("isLDrawing", "undefined")
                .build();

        LOGGER.debug("getJobs() URL: {}", uri.toString());

        HttpGet httpGet = new HttpGet(uri);
        setHeadersOnGetRequest(httpGet);

        try (CloseableHttpResponse response = httpClient.execute(httpGet, context)) {
            LOGGER.debug("Recieved response: {}", response.getStatusLine());
            HttpEntity entity = response.getEntity();
            String responseBodyJson = EntityUtils.toString(entity);
            //LOGGER.debug("Recieved response BODY {}", responseBodyJson);

            Type collectionType = new TypeToken<Collection<Job>>() {
            }.getType();
            Collection<Job> collection = gson.fromJson(responseBodyJson, collectionType);

            return collection;
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


}
