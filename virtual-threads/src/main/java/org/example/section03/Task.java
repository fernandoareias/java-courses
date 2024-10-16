package org.example.section03;

import org.example.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Task {
    private static final Logger logger = LoggerFactory.getLogger(Task.class);

    public static void cpuIntensive(int i)
    {
        logger.info("starting CPU task. Thread info: {}", Thread.currentThread());
        var timeTaken = CommonUtils.timer(() -> findFib(i));
        logger.info("ending CPU task. Thread info: {}. Time taken: {} ms", Thread.currentThread(), timeTaken);
    }

    public static long findFib(long input)
    {
        if(input < 2)
            return input;

        return findFib(input - 1) + findFib(input - 2);
    }

    public static long timer(Runnable runnable)
    {
        var start = System.currentTimeMillis();
        runnable.run();
        var end = System.currentTimeMillis();
        return (end - start);
    }

}
