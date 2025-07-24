package com.mitienda.inventario_service.service;

import com.mitienda.inventario_service.client.ProductoClient;
import com.mitienda.inventario_service.dto.CompraRequest;
import com.mitienda.inventario_service.dto.CompraResponse;
import com.mitienda.inventario_service.model.Inventario;
import com.mitienda.inventario_service.repository.InventarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompraServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @Mock
    private ProductoClient productoClient;

    @InjectMocks
    private CompraService compraService;

    @Test
    void testCompraExitosa() {
        Long productoId = 1L;
        int cantidadSolicitada = 2;
        Map<String, Object> productoMock = Map.of(
                "id", productoId,
                "nombre", "Laptop",
                "precio", 5000.0,
                "descripcion", "Laptop con gráfica dedicada"
        );
        Inventario inventario = new Inventario();
        inventario.setProductoId(productoId);
        inventario.setCantidad(10);
        when(productoClient.obtenerProductoPorId(productoId)).thenReturn(productoMock);
        when(inventarioRepository.findByProductoId(productoId)).thenReturn(inventario);
        when(inventarioRepository.save(any(Inventario.class))).thenAnswer(invocation -> invocation.getArgument(0));
        CompraRequest request = new CompraRequest();
        request.setProductoId(1L);
        request.setCantidad(2);
        CompraResponse response = compraService.realizarCompra(request);
        assertEquals("Compra realizada exitosamente.", response.getMensaje());
        assertEquals(productoId, response.getProductoId());
        assertEquals(cantidadSolicitada, response.getCantidadComprada());
        verify(inventarioRepository).save(any(Inventario.class));
    }

}