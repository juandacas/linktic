package com.mitienda.inventario_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mitienda.inventario_service.dto.CompraRequest;
import com.mitienda.inventario_service.dto.CompraResponse;
import com.mitienda.inventario_service.dto.InventarioRequest;
import com.mitienda.inventario_service.dto.InventarioResponse;
import com.mitienda.inventario_service.service.CompraService;
import com.mitienda.inventario_service.service.InventarioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = InventarioController.class)
@Import(InventarioController.class)
class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventarioService inventarioService;

    @MockBean
    private CompraService compraService; // si el constructor lo requiere

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCrearInventario() throws Exception {
        InventarioRequest request = new InventarioRequest(1L, 10);
        InventarioResponse mockResponse = new InventarioResponse(1L, 10);
        Mockito.when(inventarioService.crearInventario(any(InventarioRequest.class))).thenReturn(mockResponse);
        mockMvc.perform(post("/inventario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productoId").value(1L))
                .andExpect(jsonPath("$.cantidad").value(10));
    }

    @Test
    void testConsultarInventario() throws Exception {
        Long productoId = 1L;
        InventarioResponse response = new InventarioResponse(productoId, 20);
        Mockito.when(inventarioService.obtenerInventario(productoId)).thenReturn(response);
        mockMvc.perform(get("/inventario/{productoId}", productoId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productoId").value(1))
                .andExpect(jsonPath("$.cantidad").value(20));
    }

    @Test
    void testActualizarInventario() throws Exception {
        // Arrange
        Long productoId = 1L;
        InventarioRequest request = new InventarioRequest(productoId, 30);
        InventarioResponse response = new InventarioResponse(productoId, 30);

        Mockito.when(inventarioService.actualizarInventario(eq(productoId), any(InventarioRequest.class)))
                .thenReturn(response);

        // Act & Assert
        mockMvc.perform(put("/inventario/{productoId}", productoId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productoId").value(1))
                .andExpect(jsonPath("$.cantidad").value(30));
    }

    @Test
    void testRealizarCompra() throws Exception {
        CompraRequest request = new CompraRequest();
        CompraResponse response = new CompraResponse(1L, 2, "Compra realizada exitosamente.");
        Mockito.when(compraService.realizarCompra(any(CompraRequest.class))).thenReturn(response);
        mockMvc.perform(post("/inventario/compras")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productoId").value(1))
                .andExpect(jsonPath("$.cantidadComprada").value(2))
                .andExpect(jsonPath("$.mensaje").value("Compra realizada exitosamente."));
    }

}