package org.example.section10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class ScopedValues {
    private static final Logger logger = LoggerFactory.getLogger(ThreadLocal.class);

    private static final java.lang.ScopedValue<String> SESSION_TOKEN = ScopedValue.newInstance();

    public static void main(String[] args) {

        logger.info("value={}", SESSION_TOKEN.isBound());
        logger.info("value={}", SESSION_TOKEN.orElse("default value"));

        processIncomingRequest();
    }


    private static void processIncomingRequest(){
        var token = authenticate();

        ScopedValue.runWhere(SESSION_TOKEN, token, () -> controller());

        controller();
    }

    private static void controller() {
        logger.info("token={}", SESSION_TOKEN.get());
        service();
    }

    private static void service() {
        logger.info("token={} | Service", SESSION_TOKEN.get());
        logger.info("token={} | Service", SESSION_TOKEN.orElse("default value"));

        Thread.ofPlatform().start(() -> callExternalService());
    }

    private static void callExternalService() {
        logger.info("token={}, HTTP REQUEST", SESSION_TOKEN.get());
    }

    private static String authenticate() {
        var token = UUID.randomUUID().toString();
        logger.info("token={} | Authenticate", token);
        return token;
    }

}
