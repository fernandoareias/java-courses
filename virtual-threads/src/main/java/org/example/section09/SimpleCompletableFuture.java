package org.example.section09;

import org.example.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class SimpleCompletableFuture {

    private final static Logger logger = LoggerFactory.getLogger(SimpleCompletableFuture.class);
    public static void main(String[] args){
        logger.info("main starts");
        var cf = slowTask();
        cf.thenAccept(v -> logger.info("value={}", v));
        logger.info("Value received {}", cf.join());
        logger.info("main ends");
        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    private static CompletableFuture<String> fastTask(){
        logger.info("method starts");
        var cf = new CompletableFuture<String>();
        cf.complete("hi");
        logger.info("method ends");
        return cf;
    }


    private static CompletableFuture<String> slowTask(){
        logger.info("method starts");
        var cf = new CompletableFuture<String>();
        Thread.ofVirtual().start(() -> {
            CommonUtils.sleep(Duration.ofSeconds(2));
            cf.complete("hi");
        });
        logger.info("method ends");
        return cf;
    }
}
