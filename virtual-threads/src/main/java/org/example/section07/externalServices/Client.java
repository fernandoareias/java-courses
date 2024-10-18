package org.example.section07.externalServices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;


public class Client {
    public static final Logger log = LoggerFactory.getLogger(Client.class);

    public static final String PRODUCT_REQUEST_FORMAT = "http://localhost:7070/products/%d";
    public static final String RATING_REQUEST_FORMAT = "http://localhost:7070/ratings/%d";

    public static String getProduct(int id){
        return callExternalService(PRODUCT_REQUEST_FORMAT.formatted(id));
    }

    public static Integer getRating(int id){
        return Integer.parseInt(callExternalService(RATING_REQUEST_FORMAT.formatted(id)));
    }

    public static String callExternalService(String url) {
        log.info("calling {}", url);
        try(var stream = URI.create(url).toURL().openStream())
        {
            return new String(stream.readAllBytes());
        }catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
