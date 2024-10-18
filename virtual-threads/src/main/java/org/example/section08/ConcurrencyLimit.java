package org.example.section08;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ConcurrencyLimit {

    public static final Logger logger = LoggerFactory.getLogger(ConcurrencyLimit.class);

    public static void main(String[] args) {
        var factory = Thread.ofVirtual().name("vins", 1).factory();
        execute(Executors.newFixedThreadPool(3, factory), 20);

    }

    public static void execute(ExecutorService executorService, int taskCount)
    {
        try(executorService)
        {
            for(int i = 1; i < taskCount; i++)
            {
                int j = i;
                executorService.submit(() -> printProductionItem(j));
            }

            logger.info("Subimited");
        }
    }


    public static void printProductionItem(long id){
        logger.info("Product {}", id);
    }
}
