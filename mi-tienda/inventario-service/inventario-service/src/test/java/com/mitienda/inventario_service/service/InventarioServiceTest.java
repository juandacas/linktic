package com.mitienda.inventario_service.service;

import com.mitienda.inventario_service.client.ProductoClient;
import com.mitienda.inventario_service.dto.CompraRequest;
import com.mitienda.inventario_service.dto.CompraResponse;
import com.mitienda.inventario_service.dto.InventarioRequest;
import com.mitienda.inventario_service.dto.InventarioResponse;
import com.mitienda.inventario_service.model.Inventario;
import com.mitienda.inventario_service.repository.InventarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private ProductoClient productoClient;

    @InjectMocks
    private InventarioService inventarioService;

    @Test
    void testCrearInventarioExitoso() {
        InventarioRequest request = new InventarioRequest(1L, 10);
        when(inventarioRepository.findByProductoId(1L)).thenReturn(null);
        Inventario inventarioGuardado = new Inventario();
        inventarioGuardado.setProductoId(1L);
        inventarioGuardado.setCantidad(10);
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventarioGuardado);
        InventarioResponse response = inventarioService.crearInventario(request);
        assertNotNull(response);
        assertEquals(1L, response.getProductoId());
        assertEquals(10, response.getCantidad());
    }

    @Test
    void testCrearInventarioYaExiste() {
        InventarioRequest request = new InventarioRequest(1L, 10);
        Inventario existente = new Inventario();
        existente.setProductoId(1L);
        existente.setCantidad(5);
        when(inventarioRepository.findByProductoId(1L)).thenReturn(existente);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            inventarioService.crearInventario(request);
        });
        assertEquals("El inventario para este producto ya existe", exception.getMessage());
    }

    @Test
    void testObtenerInventarioExitoso() {
        Long productoId = 1L;
        Inventario inventario = new Inventario();
        inventario.setProductoId(productoId);
        inventario.setCantidad(15);
        when(inventarioRepository.findById(productoId)).thenReturn(Optional.of(inventario));
        InventarioResponse response = inventarioService.obtenerInventario(productoId);
        assertNotNull(response);
        assertEquals(productoId, response.getProductoId());
        assertEquals(15, response.getCantidad());
    }

    @Test
    void testActualizarInventarioExistente() {
        Long productoId = 1L;
        Inventario existente = new Inventario(productoId, 5);
        InventarioRequest request = new InventarioRequest();
        request.setProductoId(productoId);
        request.setCantidad(20);
        when(inventarioRepository.findById(productoId)).thenReturn(Optional.of(existente));
        when(inventarioRepository.save(any(Inventario.class))).thenAnswer(i -> i.getArgument(0));
        InventarioResponse response = inventarioService.actualizarInventario(productoId, request);
        assertNotNull(response);
        assertEquals(productoId, response.getProductoId());
        assertEquals(20, response.getCantidad());
    }

    @Test
    void testCompraExitosa() {
        Long productoId = 1L;
        Integer cantidad = 3;
        CompraRequest request = new CompraRequest();
        request.setProductoId(productoId);
        request.setCantidad(cantidad);
        Inventario inv = new Inventario(productoId, 10);
        when(inventarioRepository.findById(productoId)).thenReturn(Optional.of(inv));
        when(inventarioRepository.save(any(Inventario.class))).thenAnswer(i -> i.getArgument(0));
        CompraResponse response = inventarioService.comprar(request);
        assertNotNull(response);
        assertEquals(productoId, response.getProductoId());
        assertEquals(cantidad, response.getCantidadComprada());
        assertEquals("Compra realizada exitosamente.", response.getMensaje());
    }

    @Test
    void testCompraProductoNoEncontrado() {
        Long productoId = 1L;
        CompraRequest request = new CompraRequest();
        request.setProductoId(productoId);
        request.setCantidad(2);
        doThrow(new RuntimeException("404")).when(productoClient).obtenerProductoPorId(productoId);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            inventarioService.comprar(request);
        });
        assertEquals("Producto no encontrado con ID: " + productoId, ex.getMessage());
    }

    @Test
    void testCompraInventarioNoDisponible() {
        Long productoId = 1L;
        CompraRequest request = new CompraRequest();
        request.setProductoId(productoId);
        request.setCantidad(5);
        when(inventarioRepository.findById(productoId)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            inventarioService.comprar(request);
        });
        assertEquals("Inventario no disponible para productoId: " + productoId, ex.getMessage());
    }

    @Test
    void testCompraInventarioInsuficiente() {
        Long productoId = 1L;
        CompraRequest request = new CompraRequest();
        request.setProductoId(productoId);
        request.setCantidad(10);
        Inventario inv = new Inventario(productoId, 5);
        when(inventarioRepository.findById(productoId)).thenReturn(Optional.of(inv));
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            inventarioService.comprar(request);
        });
        assertEquals("Inventario insuficiente para productoId: " + productoId, ex.getMessage());
    }

}