package com.fernando.first_batch.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;

@Configuration
@PropertySource("file:/etc/config/primeirojobbatch/application.properties")
public class PropsConfig {

    @Bean
    public PropertySourcesPlaceholderConfigurer configurer(){
        var configure = new PropertySourcesPlaceholderConfigurer();
        configure.setLocation(new FileSystemResource("/etc/config/primeirojobbatch/application.properties"));
        return configure;
    }
}
