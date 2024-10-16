package org.example.section05;

import org.example.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class RaceConditionWithSyncWithIOWithPinnedThreads {

    private static final Logger logger = LoggerFactory.getLogger(RaceConditionWithSyncWithIOWithPinnedThreads.class);

    private static final List<Integer> list = new ArrayList<Integer>();

    static {
        System.setProperty("jdk.tracePinnedThreads", "full");
    }

    //psvm
    public static void main(String[] args) {
        Runnable runnable = () -> logger.info("*** Test Message ***");
        demo(Thread.ofVirtual());
        Thread.ofVirtual().start(runnable);
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
