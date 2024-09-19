package com.fernando.first.spring.app.application.gateways;


import com.fernando.first.spring.app.domain.entity.User;

public interface UserGateway {

    User createUser(User user);
}
