package com.fernando.first.spring.app.infrastructure.gateways;

import com.fernando.first.spring.app.domain.entity.User;
import com.fernando.first.spring.app.infrastructure.persistence.UserEntity;

public class UserEntityMapper {
    UserEntity toEntity(User user)
    {
        return new UserEntity(user.username(), user.email());
    }

    User toDomainObject(UserEntity entity)
    {
        return new User(entity.getName(), entity.getEmail());
    }
}
