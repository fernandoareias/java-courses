package org.example.section01;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;


public class Task {
    private static final Logger log = LoggerFactory.getLogger(Task.class);

    public static void ioIntensive(int i)  {
        try{
            log.info("Starting I/O Task {}", i);

            Thread.sleep(Duration.ofSeconds(10));

            log.info("Ending I/O Task {}", i);
        }catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }
}
