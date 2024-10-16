package org.example.section05;

import org.example.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class RaceConditionWithSyncWithIO {

    private static final Logger logger = LoggerFactory.getLogger(RaceConditionWithSyncWithIO.class);

    private static final List<Integer> list = new ArrayList<Integer>();

    //psvm
    public static void main(String[] args) {
        Runnable runnable = () -> logger.info("*** Test Message ***");
        demo(Thread.ofPlatform());
        Thread.ofPlatform().start(runnable);
        CommonUtils.sleep(Duration.ofSeconds(15));
    }

    private static void demo(Thread.Builder builder)
    {
        for (int i = 0; i < 50; i ++)
        {
            builder.start(() -> {
                logger.info("Thread started. {}", Thread.currentThread());
                ioTask();
                logger.info("Thread ended. {}", Thread.currentThread());
            });
        }
    }

    private static synchronized void ioTask(){
        list.add(1);
        CommonUtils.sleep(Duration.ofSeconds(10));
    }

}
