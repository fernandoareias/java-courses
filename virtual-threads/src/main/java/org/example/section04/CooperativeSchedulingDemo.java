package org.example.section04;

import org.example.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class CooperativeSchedulingDemo {
    private static final Logger logger = LoggerFactory.getLogger(CooperativeSchedulingDemo.class);

    static {
        System.setProperty("jdk.virtualThreadScheduler.parallelism", "1");
        System.setProperty("jdk.virtualThreadScheduler.maxPoolSize", "1");
    }

    public static void main(String[] args){
        var virtual = Thread.ofVirtual();
        var t1 = virtual.unstarted(() -> demo(1));
        var t2 = virtual.unstarted(() -> demo(2));

        t1.start();
        t2.start();

        CommonUtils.sleep(Duration.ofSeconds(2));
    }

    private static void demo(int threadNumber){
        logger.info("thread-{} started", threadNumber);
        for(int i = 0; i < 10; i++)
        {
            logger.info("thread-{} is printing {}. Thread info: {}", threadNumber, i, Thread.currentThread());
            Thread.yield();
        }

        logger.info("thread-{} ended", threadNumber);
    }
}
