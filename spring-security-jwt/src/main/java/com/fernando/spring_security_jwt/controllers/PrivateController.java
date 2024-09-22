package com.fernando.spring_security_jwt.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/private")
public class PrivateController {

    @GetMapping
    public String getMessage() {
        return "private hello";
    }

}
