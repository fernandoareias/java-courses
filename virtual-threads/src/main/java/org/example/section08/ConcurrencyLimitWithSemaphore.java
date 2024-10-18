package org.example.section08;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ConcurrencyLimitWithSemaphore {
    public static final Logger logger = LoggerFactory.getLogger(ConcurrencyLimit.class);

    public static void main(String[] args) throws InterruptedException {
        var semaphore = new Semaphore(3);

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        executeWithSemaphore(executorService, 10, semaphore);
    }

    public static void executeWithSemaphore(ExecutorService executorService, int taskCount, Semaphore semaphore) {
        try (executorService) {
            for (int i = 1; i <= taskCount; i++) {
                int taskId = i;

                executorService.submit(() -> {
                    try {
                        semaphore.acquire();
                        logger.info("Executing task {}", taskId);
                        printProductionItem(taskId);

                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        logger.error("Task interrupted", e);
                    } finally {
                        semaphore.release();
                        logger.info("Task {} completed, semaphore released", taskId);
                    }
                });
            }
        }
    }

    public static void printProductionItem(long id) {
        logger.info("Producing item {}", id);
    }
}
