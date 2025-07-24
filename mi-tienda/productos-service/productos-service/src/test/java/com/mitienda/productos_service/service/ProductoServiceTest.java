package com.mitienda.productos_service.service;

import com.mitienda.productos_service.dto.ProductoRequest;
import com.mitienda.productos_service.dto.ProductoResponse;
import com.mitienda.productos_service.model.Producto;
import com.mitienda.productos_service.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    private static final String NOMBRE_PRODUCTO = "Laptop Gamer";
    private static final double PRECIO_PRODUCTO = 5000.0;
    private static final String DESCRIPCION_PRODUCTO = "Laptop con gráfica dedicada";
    private static final long ID_PRODUCTO = 1L;

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @Test
    void testCrearProducto() {
        ProductoRequest request = new ProductoRequest();
        request.setNombre(NOMBRE_PRODUCTO);
        request.setPrecio(PRECIO_PRODUCTO);
        request.setDescripcion(DESCRIPCION_PRODUCTO);
        Producto productoGuardado = Producto.builder()
                .id(ID_PRODUCTO)
                .nombre(NOMBRE_PRODUCTO)
                .precio(PRECIO_PRODUCTO)
                .descripcion(DESCRIPCION_PRODUCTO)
                .build();
        when(productoRepository.save(org.mockito.ArgumentMatchers.any(Producto.class)))
                .thenReturn(productoGuardado);
        ProductoResponse response = productoService.crearProducto(request);
        assertNotNull(response);
        assertEquals(ID_PRODUCTO, response.getId());
        assertEquals(NOMBRE_PRODUCTO, response.getNombre());
        assertEquals(PRECIO_PRODUCTO, response.getPrecio());
        assertEquals(DESCRIPCION_PRODUCTO, response.getDescripcion());
    }

    @Test
    void testObtenerProductoPorId_Existente() {
        Producto producto = Producto.builder()
                .id(ID_PRODUCTO)
                .nombre(NOMBRE_PRODUCTO)
                .precio(PRECIO_PRODUCTO)
                .descripcion(DESCRIPCION_PRODUCTO)
                .build();

        when(productoRepository.findById(ID_PRODUCTO)).thenReturn(Optional.of(producto));
        Optional<ProductoResponse> resultado = productoService.obtenerProductoPorId(ID_PRODUCTO);
        assertTrue(resultado.isPresent());
        ProductoResponse response = resultado.get();
        assertEquals(ID_PRODUCTO, response.getId());
        assertEquals(NOMBRE_PRODUCTO, response.getNombre());
        assertEquals(PRECIO_PRODUCTO, response.getPrecio());
        assertEquals(DESCRIPCION_PRODUCTO, response.getDescripcion());
    }

    @Test
    void testListarTodos() {
        Producto producto1 = Producto.builder()
                .id(1L)
                .nombre("Producto 1")
                .precio(1000.0)
                .descripcion("Descripción 1")
                .build();
        Producto producto2 = Producto.builder()
                .id(2L)
                .nombre("Producto 2")
                .precio(2000.0)
                .descripcion("Descripción 2")
                .build();
        List<Producto> productos = List.of(producto1, producto2);
        when(productoRepository.findAll()).thenReturn(productos);
        List<ProductoResponse> resultado = productoService.listarTodos();
        assertEquals(2, resultado.size());

        ProductoResponse resp1 = resultado.get(0);
        assertEquals(1L, resp1.getId());
        assertEquals("Producto 1", resp1.getNombre());
        assertEquals(1000.0, resp1.getPrecio());
        assertEquals("Descripción 1", resp1.getDescripcion());

        ProductoResponse resp2 = resultado.get(1);
        assertEquals(2L, resp2.getId());
        assertEquals("Producto 2", resp2.getNombre());
        assertEquals(2000.0, resp2.getPrecio());
        assertEquals("Descripción 2", resp2.getDescripcion());
    }

}