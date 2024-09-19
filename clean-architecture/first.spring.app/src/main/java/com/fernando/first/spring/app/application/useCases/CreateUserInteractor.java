package com.fernando.first.spring.app.application.useCases;

import com.fernando.first.spring.app.application.gateways.UserGateway;
import com.fernando.first.spring.app.domain.entity.User;

public class CreateUserInteractor {

    private UserGateway userGateway;

    public CreateUserInteractor(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public User createUser(String username, String email)
    {
        return userGateway.createUser(new User(username, email));
    }
}
