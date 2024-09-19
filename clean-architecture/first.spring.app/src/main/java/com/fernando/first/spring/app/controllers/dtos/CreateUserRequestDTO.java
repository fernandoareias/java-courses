package com.fernando.first.spring.app.controllers.dtos;

import java.io.Serializable;

public record CreateUserRequestDTO(String name, String email){}
