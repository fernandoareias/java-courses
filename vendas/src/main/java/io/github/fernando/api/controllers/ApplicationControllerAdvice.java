package io.github.fernando.api.controllers;


import io.github.fernando.api.ApiErrors;
import io.github.fernando.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(DomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleDomainException(DomainException ex){
        return new ApiErrors(ex.getMessage());
    }
}
