package com.fernando.gateway_service.configurations;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigParameters;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenApiConfiguration {

    @Bean
    @Lazy(value = false)
    public List<GroupedOpenApi> apis(SwaggerUiConfigParameters swaggerUiConfigParameters,
                                     RouteDefinitionLocator locator) {

        var definitions = locator.getRouteDefinitions().collectList().block();
        List<GroupedOpenApi> groupedOpenApis = new ArrayList<>();

        if (definitions != null) {
            definitions.stream()
                    .filter(routeDefinition -> routeDefinition.getId().matches(".*-service"))
                    .forEach(routeDefinition -> {
                        String name = routeDefinition.getId();
                        swaggerUiConfigParameters.addGroup(name);
                        GroupedOpenApi groupedOpenApi = GroupedOpenApi.builder()
                                .pathsToMatch("/" + name + "/**")
                                .group(name)
                                .build();
                        groupedOpenApis.add(groupedOpenApi);
                    });
        }

        return groupedOpenApis;
    }
}
