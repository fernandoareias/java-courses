package com.fernando.book_service.controllers;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import io.github.resilience4j.retry.annotation.Retry;

@RestController
@RequestMapping("book-service")
public class FooBarController {

    @GetMapping("/foo-bar")
    //@Retry(name = "default", fallbackMethod = "fallbackMethod")
    //@CircuitBreaker(name = "default", fallbackMethod = "fallbackMethod")
    @RateLimiter(name = "default", fallbackMethod = "fallbackMethod")
    public String name(){

        var response = new RestTemplate().getForEntity("http://localhost:8080/foo-bar", String.class);

        return "Foo-Bar";
    }


    public String fallbackMethod(Exception ex){
        return "fallbackMethod Foo-Bar!";
    }

}
