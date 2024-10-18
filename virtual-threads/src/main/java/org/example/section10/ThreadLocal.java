package org.example.section10;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class ThreadLocal {

    private static final Logger logger = LoggerFactory.getLogger(ThreadLocal.class);

    //private static final java.lang.ThreadLocal<String> SESSION_TOKEN = new java.lang.ThreadLocal<>();
    private static final java.lang.ThreadLocal<String> SESSION_TOKEN = new java.lang.InheritableThreadLocal<>();

    public static void main(String[] args) {
        Thread.ofPlatform().start(() -> processIncomingRequest());
        Thread.ofPlatform().start(() -> processIncomingRequest());
    }

    private static void processIncomingRequest(){
        authenticate();
        controller();
    }

    private static void controller() {
        logger.info("token={}", SESSION_TOKEN.get());
        service();
    }

    private static void service() {
        logger.info("token={} | Service", SESSION_TOKEN.get());

        Thread.ofPlatform().start(() -> callExternalService());
    }

    private static void callExternalService() {
        logger.info("token={}, HTTP REQUEST", SESSION_TOKEN.get());
    }

    private static void authenticate() {
        var token = UUID.randomUUID().toString();
        logger.info("token={} | Authenticate", token);
        SESSION_TOKEN.set(token);
    }




}
