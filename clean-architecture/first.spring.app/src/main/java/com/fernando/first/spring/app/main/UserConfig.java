package com.fernando.first.spring.app.main;

import com.fernando.first.spring.app.application.gateways.UserGateway;
import com.fernando.first.spring.app.application.useCases.CreateUserInteractor;
import com.fernando.first.spring.app.infrastructure.gateways.UserEntityMapper;
import com.fernando.first.spring.app.infrastructure.gateways.UserRepositoryGateway;
import com.fernando.first.spring.app.infrastructure.persistence.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    CreateUserInteractor createUserCase(UserGateway userGateway)
    {
        return new CreateUserInteractor(userGateway);
    }

    @Bean
    UserGateway userGateway(UserRepository userRepository, UserEntityMapper userEntityMapper) {
        return new UserRepositoryGateway(userRepository, userEntityMapper);
    }

    @Bean
    UserEntityMapper userEntityMapper() {
        return new UserEntityMapper();
    }
}
