package com.fernando.course.resources.exceptions;

import com.fernando.course.services.exceptions.DatabaseException;
import com.fernando.course.services.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException ex, HttpServletRequest req)
    {
        String error = "Resource not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        var err = new StandardError(Instant.now(), status, error, ex.getMessage(), req.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> database(DatabaseException ex, HttpServletRequest req)
    {
        String error = "Database error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        var err = new StandardError(Instant.now(), status, error, ex.getMessage(), req.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

}
