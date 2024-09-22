package com.fernando.spring_security_jwt.controllers;

import com.fernando.spring_security_jwt.services.AuthenticationServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationServices authenticationServices;

    @PostMapping
    public String authenticate(Authentication authentication) {
        return authenticationServices.authenticate(authentication);
    }

}
