//package com.example.gateway_service.config;
//
//import io.swagger.v3.oas.annotations.OpenAPIDefinition;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.info.Info;
//import io.swagger.v3.oas.models.servers.Server;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.cloud.util.ConditionalOnBootstrapEnabled;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//
//import java.util.List;
//
//@Configuration
//public class OpenAPIConfig {
//    @Bean
//    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
//        return builder
//                .routes()
//                .route(r -> r.path("/iam/v3/api-docs")
//                        .and().method(HttpMethod.GET)
//                        .uri("lb://security-demo"))
//
//                .route(r -> r.path("/storage/v3/api-docs")
//                        .and().method(HttpMethod.GET)
//                        .uri("lb://storage"))
//                .build();
//    }
//}
