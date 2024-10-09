package com.springbatch.remotechunkingjob.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrokerConfig {

    @Value("${broker.url}")
    private String url;

    @Value("${broker.username}")
    private String username;

    @Value("${broker.password}")
    private String password;

    @Bean
    public ActiveMQConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory();
        factory.setBrokerURL(url);
        factory.setUserName(username);
        factory.setPassword(password);
        factory.setTrustAllPackages(true);

        return factory;
    }
}
