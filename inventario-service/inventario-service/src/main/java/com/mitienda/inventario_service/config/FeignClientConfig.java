package com.mitienda.inventario_service.config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {
    @Bean
    public RequestInterceptor apiKeyInterceptor() {
        return requestTemplate -> requestTemplate.header("x-api-key", "clave-secreta");
    }
}
