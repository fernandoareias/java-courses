package org.example.section03;

import org.example.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;


import java.util.concurrent.CountDownLatch;

public class CPUTaskDemo {

    private static final Logger logger = LoggerFactory.getLogger(CPUTaskDemo.class);
    private static final int TASKS_COUNT = 2 * Runtime.getRuntime().availableProcessors();

    public static void main(String[] args)
    {
        logger.info("Tasks count: {}", TASKS_COUNT);
        for(int i = 0; i < 3; i++)
        {
            var totalTimeTaken = CommonUtils.timer(() -> demo(Thread.ofVirtual()));
            logger.info("Total time taken with virtual {} ms", totalTimeTaken);

            totalTimeTaken = CommonUtils.timer(() -> demo(Thread.ofVirtual()));
            logger.info("Total time taken with platform {} ms", totalTimeTaken);

        }


        demo(Thread.ofPlatform());
    }

    private static void demo(Thread.Builder builder)
    {
        var latch = new CountDownLatch(TASKS_COUNT);
        for (int i = 0; i < TASKS_COUNT; i++)
        {
            builder.start(() -> {
                Task.cpuIntensive(8);
                latch.countDown();
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

