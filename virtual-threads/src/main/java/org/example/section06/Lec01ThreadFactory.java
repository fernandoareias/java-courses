package org.example.section06;

import org.example.section05.ReetrantLockWithIO;
import org.example.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Lec01ThreadFactory {

    private static final Logger logger = LoggerFactory.getLogger(ReetrantLockWithIO.class);
    private static final List<Integer> list = new ArrayList<Integer>();

    public static void main(String[] args) {
        demo(Thread.ofVirtual().name("vins", 1).factory());

        CommonUtils.sleep(Duration.ofSeconds(3));
    }

    private static void demo(ThreadFactory factory)
    {
        for (int i = 0; i < 3; i ++)
        {
            var t = factory.newThread(() -> {
                logger.info("Thread started. {}", Thread.currentThread());

                var ct = factory.newThread(() -> {
                    logger.info("Child Thread started. {}", Thread.currentThread());
                    CommonUtils.sleep(Duration.ofSeconds(2));
                    logger.info("Child Thread ended. {}", Thread.currentThread());
                });

                ct.start();
                logger.info("Thread ended. {}", Thread.currentThread());
            });

            t.start();
        }
    }

}
