package com.fernando.first.spring.app.controllers;

import com.fernando.first.spring.app.application.useCases.CreateUserInteractor;
import com.fernando.first.spring.app.controllers.dtos.CreateUserRequestDTO;
import com.fernando.first.spring.app.controllers.dtos.CreateUserResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/users")
public class UserController {

    private CreateUserInteractor interactor;

    public UserController(CreateUserInteractor interactor) {
        this.interactor = interactor;
    }


    @PostMapping
    public ResponseEntity<CreateUserResponseDTO> createUser(@RequestBody CreateUserRequestDTO request)
    {
        var response = interactor.createUser(request.name(), request.email());

        if(response == null)
            return ResponseEntity.badRequest().build();

        var view = new CreateUserResponseDTO(response.username(), response.email());

        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.username()).toUri();

        return ResponseEntity.created(uri).body(view);
    }


}
