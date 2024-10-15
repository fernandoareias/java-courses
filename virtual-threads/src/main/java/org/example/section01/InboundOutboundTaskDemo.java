package org.example.section01;

import java.util.concurrent.CountDownLatch;

public class InboundOutboundTaskDemo {
    private static final int MAX_PLATFORM = 10;
    private static final int MAX_VIRTUAL = 10;

    public static void main(String[] args) throws InterruptedException {
        virtualThreadDemo4();
    }
    // To create platform thread
    private static void platformThreadDemo1(){
        for(int i = 0; i < MAX_PLATFORM; i++)
        {
            int j = i;
            Thread thread = new Thread(() -> Task.ioIntensive(j));
            thread.start();
        }
    }

    // To create platform thread using builder
    private static void platformThreadDemo2(){
        var builder = Thread.ofPlatform().name("vins", 1);
        for(int i = 0; i < MAX_PLATFORM; i++)
        {
            int j = i;
            Thread thread = builder.unstarted(() -> Task.ioIntensive(j));
            thread.start();
        }
    }

    // To create platform thread using daemon in background
    private static void platformThreadDemo3() throws InterruptedException {
        var latch = new CountDownLatch(MAX_PLATFORM);
        var builder = Thread.ofPlatform().daemon().name("daemon", 1);
        for(int i = 0; i < MAX_PLATFORM; i++)
        {
            int j = i;
            Thread thread = builder.unstarted(() -> {
                Task.ioIntensive(j);
                latch.countDown();
            });
            thread.start();
        }
        latch.await();
    }


    // To create virtual thread
    // virtual thread are demon threads by default
    private static void virtualThreadDemo4(){
        var builder = Thread.ofVirtual();
        for(int i = 0; i < MAX_VIRTUAL; i++)
        {
            int j = i;
            Thread thread = builder.unstarted(() -> Task.ioIntensive(j));
            thread.start();
        }
    }
}
