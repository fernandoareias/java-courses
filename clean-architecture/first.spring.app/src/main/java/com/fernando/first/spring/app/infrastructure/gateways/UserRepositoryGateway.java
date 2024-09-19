package com.fernando.first.spring.app.infrastructure.gateways;

import com.fernando.first.spring.app.application.gateways.UserGateway;
import com.fernando.first.spring.app.domain.entity.User;
import com.fernando.first.spring.app.infrastructure.persistence.UserEntity;
import com.fernando.first.spring.app.infrastructure.persistence.UserRepository;

public class UserRepositoryGateway implements UserGateway {

    private UserRepository userRepository;
    private UserEntityMapper mapper;
    public UserRepositoryGateway(
            UserRepository userRepository,
            UserEntityMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public User createUser(User user) {
        var entity =mapper.toEntity(user);
        userRepository.save(entity);
        return mapper.toDomainObject(entity);
    }

}
