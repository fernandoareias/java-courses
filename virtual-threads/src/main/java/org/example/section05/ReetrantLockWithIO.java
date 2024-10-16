package org.example.section05;

import org.example.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReetrantLockWithIO {

    private static final Logger logger = LoggerFactory.getLogger(ReetrantLockWithIO.class);
    private static final Lock lock = new ReentrantLock();
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
        logger.info("Size {}", list.size());
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

    private static void ioTask (){

        try {
            lock.lock();
            list.add(1);
        }catch (Exception e){
            logger.error("error", e);
        }finally {
            lock.unlock();
        }
    }


}
