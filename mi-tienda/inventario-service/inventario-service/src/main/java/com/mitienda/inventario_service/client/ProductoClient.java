package com.mitienda.inventario_service.client;

import com.mitienda.inventario_service.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(
        name = "productos-service",
        url = "http://localhost:8081",
        configuration = FeignClientConfig.class
)
public interface ProductoClient {

    @GetMapping("/productos/{id}")
    Map<String, Object> obtenerProductoPorId(@PathVariable("id") Long id);

}
