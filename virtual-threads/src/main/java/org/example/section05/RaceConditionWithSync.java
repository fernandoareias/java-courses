package org.example.section05;

import org.example.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class RaceConditionWithSync {

    private static final Logger logger = LoggerFactory.getLogger(RaceConditionWithSync.class);

    private static final List<Integer> list = new ArrayList<Integer>();

    //psvm
    public static void main(String[] args) {

        demo(Thread.ofPlatform());
        CommonUtils.sleep(Duration.ofSeconds(2));
        logger.info("list size: {}", list.size());
    }

    private static void demo(Thread.Builder builder)
    {
        for (int i = 0; i < 50; i ++)
        {
            builder.start(() -> {
                logger.info("Thread started. {}", Thread.currentThread());
               for(int j = 0; j < 200; j++)
               {
                   inMemoryTask();
               }

                logger.info("Thread ended. {}", Thread.currentThread());
            });
        }
    }

    private static synchronized void inMemoryTask(){
        list.add(1);
    }

}
