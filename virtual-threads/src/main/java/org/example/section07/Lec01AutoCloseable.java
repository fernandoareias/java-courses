package org.example.section07;

import org.example.section05.ReetrantLockWithIO;
import org.example.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Lec01AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(ReetrantLockWithIO.class);

    public static void main(String[] args) {
//        var executor = Executors.newSingleThreadExecutor();
//        executor.submit(Lec01AutoCloseable::task);
//        logger.info("Submited");
//        executor.shutdown();

//        try(var executor = Executors.newSingleThreadExecutor())
//        {
//            executor.submit(Lec01AutoCloseable::task);
//            logger.info("Submited");
//
//            executor.submit(Lec01AutoCloseable::task);
//            logger.info("Submited");
//
//            executor.submit(Lec01AutoCloseable::task);
//            logger.info("Submited");
//
//            executor.submit(Lec01AutoCloseable::task);
//            logger.info("Submited");
//
//            executor.submit(Lec01AutoCloseable::task);
//            logger.info("Submited");
//
//        }

        //execute(Executors.newVirtualThreadPerTaskExecutor(), 10);

        schedule();
        CommonUtils.sleep(Duration.ofSeconds(3));
    }

    private static void schedule(){
        try(var executorService = Executors.newSingleThreadScheduledExecutor())
        {
            executorService.scheduleAtFixedRate(() -> {
               logger.info("executing task");
            }, 0, 1, TimeUnit.SECONDS);

            CommonUtils.sleep(Duration.ofSeconds(5));
        }
    }

    private static void execute(ExecutorService executorService, int taskCount)
    {
        try(executorService){
            for (int i = 0; i < taskCount; i++)
            {
                int j = i ;
                executorService.submit(() -> task(j));
            }
            logger.info("Submited");
        }

    }
    private static void task(int i){
        logger.info("Thread started: {}, Thread info: {}", i, Thread.currentThread());
        CommonUtils.sleep(Duration.ofSeconds(1));
        logger.info("Thread ended: {}, Thread info: {}", i, Thread.currentThread());
    }
}
