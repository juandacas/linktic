package com.mitienda.productos_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitienda.productos_service.dto.ProductoRequest;
import com.mitienda.productos_service.dto.ProductoResponse;
import com.mitienda.productos_service.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductoService productoService;

    private ProductoController productoController;

    @BeforeEach
    void setUp() {
        productoService = mock(ProductoService.class);
        productoController = new ProductoController(productoService);
    }

    @Test
    void testCrearProducto() throws Exception {
        ProductoRequest request = new ProductoRequest("Laptop", 5000.0, "Laptop con gráfica dedicada");
            mockMvc.perform(post("/productos")
                        .header("x-api-key", "clave-secreta") // si usas api key
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Laptop"))
                .andExpect(jsonPath("$.precio").value(5000.0))
                .andExpect(jsonPath("$.descripcion").value("Laptop con gráfica dedicada"));
    }

    @Test
    void testObtenerPorId_ProductoExistente() {
        Long productoId = 1L;
        ProductoResponse response = new ProductoResponse(productoId, "Laptop", 5000.0, "Laptop con gráfica dedicada");
        when(productoService.obtenerProductoPorId(productoId)).thenReturn(Optional.of(response));
        ResponseEntity<ProductoResponse> resultado = productoController.obtenerPorId(productoId);
        assertEquals(200, resultado.getStatusCodeValue());
        assertNotNull(resultado.getBody());
        assertEquals("Laptop", resultado.getBody().getNombre());
    }

    @Test
    void testObtenerPorId_ProductoNoExiste() {
        Long productoId = 2L;
        when(productoService.obtenerProductoPorId(productoId)).thenReturn(Optional.empty());
        ResponseEntity<ProductoResponse> resultado = productoController.obtenerPorId(productoId);
        assertEquals(404, resultado.getStatusCodeValue());
        assertNull(resultado.getBody());
    }

    @Test
    void testListarProductos() throws Exception {
        List<ProductoResponse> productos = List.of(
                new ProductoResponse(1L, "Laptop", 5000.0, "Laptop con gráfica dedicada"),
                new ProductoResponse(2L, "Mouse", 150.0, "Mouse inalámbrico")
        );

        when(productoService.listarTodos()).thenReturn(productos);

        mockMvc.perform(get("/productos")
                        .header("x-api-key", "clave-secreta")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Laptop Gamer"))
                .andExpect(jsonPath("$[0].precio").value(5000.0));
    }



}