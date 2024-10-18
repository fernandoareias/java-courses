package org.example.section09;

import org.example.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

public class RunAsyncTask {

    private final static Logger logger = LoggerFactory.getLogger(RunAsyncTask.class);
    public static void main(String[] args){
        logger.info("main starts");
        var cf = runAsync()
                .thenRun(() -> logger.info("task done"))
                .exceptionally(e -> {
                    logger.error(e.getMessage());
                    return null;
                });

        logger.info("main ends");
        CommonUtils.sleep(Duration.ofSeconds(12));
    }

    private static CompletableFuture<Void> runAsync(){
        logger.info("method starts");

        var cf = CompletableFuture.runAsync(() -> {
            CommonUtils.sleep(Duration.ofSeconds(10));
            logger.info("Task completed");
        }, Executors.newVirtualThreadPerTaskExecutor());

        logger.info("method ends");
        return cf;
    }

}
