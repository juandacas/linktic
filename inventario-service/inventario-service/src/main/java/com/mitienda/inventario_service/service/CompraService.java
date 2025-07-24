package com.mitienda.inventario_service.service;

import com.mitienda.inventario_service.client.ProductoClient;
import com.mitienda.inventario_service.dto.CompraRequest;
import com.mitienda.inventario_service.dto.CompraResponse;
import com.mitienda.inventario_service.model.Inventario;
import com.mitienda.inventario_service.repository.InventarioRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CompraService {

    private final InventarioRepository inventarioRepository;
    private final ProductoClient productoClient;

    public CompraService(InventarioRepository inventarioRepository, ProductoClient productoClient) {
        this.inventarioRepository = inventarioRepository;
        this.productoClient = productoClient;
    }

    public CompraResponse realizarCompra(CompraRequest request) {
        // 1. Validar que el producto exista
        Map<String, Object> producto = productoClient.obtenerProductoPorId(request.getProductoId());
        if (producto == null || producto.isEmpty()) {
            return new CompraResponse(request.getProductoId(), request.getCantidad(),"El producto no existe.");
        }

        // 2. Buscar inventario
        Inventario inventario = inventarioRepository.findByProductoId(request.getProductoId());
        if (inventario == null) {
            return new CompraResponse(request.getProductoId(), request.getCantidad(),"No hay inventario registrado para este producto.");
        }

        // 3. Verificar stock
        if (inventario.getCantidad() < request.getCantidad()) {
            return new CompraResponse(request.getProductoId(), request.getCantidad(),"Stock insuficiente.");
        }

        // 4. Actualizar stock
        inventario.setCantidad(inventario.getCantidad() - request.getCantidad());
        inventarioRepository.save(inventario);

        return new CompraResponse(request.getProductoId(), request.getCantidad(),"Compra realizada exitosamente.");
    }
}
