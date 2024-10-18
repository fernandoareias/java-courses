package org.example.section07;

import org.example.section07.externalServices.Client;

import java.util.concurrent.*;

public class AccessResponseMain {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        try(var executor = Executors.newVirtualThreadPerTaskExecutor()){
            var future = executor.submit(() -> Client.getProduct(1));
            future.get(100, TimeUnit.SECONDS);
        }
    }
}
