package com.orderapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Order Management API")
                        .description("Production-ready REST API for creating, tracking, and updating customer orders. Lifecycle: PENDING → PROCESSING → SHIPPED → DELIVERED (or CANCELLED).")
                        .version("1.0.0")
                        .contact(new Contact().name("Order Management Team")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Local development"),
                        new Server().url("http://order-service.local").description("Kubernetes cluster")
                ));
    }
}
