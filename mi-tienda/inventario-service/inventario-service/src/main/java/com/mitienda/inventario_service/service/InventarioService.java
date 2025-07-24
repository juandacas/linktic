package com.mitienda.inventario_service.service;

import com.mitienda.inventario_service.client.ProductoClient;
import com.mitienda.inventario_service.dto.CompraRequest;
import com.mitienda.inventario_service.dto.CompraResponse;
import com.mitienda.inventario_service.dto.InventarioRequest;
import com.mitienda.inventario_service.dto.InventarioResponse;
import com.mitienda.inventario_service.model.Inventario;
import com.mitienda.inventario_service.repository.InventarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InventarioService {

    private final InventarioRepository inventarioRepository;
    private final ProductoClient productoClient;

    public InventarioService(InventarioRepository inventarioRepository, ProductoClient productoClient) {
        this.inventarioRepository = inventarioRepository;
        this.productoClient = productoClient;
    }

    public InventarioResponse crearInventario(InventarioRequest request) {
        // Verificar si ya existe inventario para ese producto
        Inventario existente = inventarioRepository.findByProductoId(request.getProductoId());
        if (existente != null) {
            throw new RuntimeException("El inventario para este producto ya existe");
        }

        Inventario nuevo = new Inventario();
        nuevo.setProductoId(request.getProductoId());
        nuevo.setCantidad(request.getCantidad());

        Inventario guardado = inventarioRepository.save(nuevo);

        return new InventarioResponse(guardado.getProductoId(), guardado.getCantidad());
    }

    public InventarioResponse obtenerInventario(Long productoId) {
        Inventario inv = inventarioRepository.findById(productoId)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado para productoId " + productoId));

        return mapToResponse(inv);
    }

    public InventarioResponse actualizarInventario(Long productoId, InventarioRequest request) {
        Inventario inv = inventarioRepository.findById(productoId)
                .orElse(new Inventario(productoId, 0));

        inv.setCantidad(request.getCantidad());
        inventarioRepository.save(inv);

        return mapToResponse(inv);
    }

    public CompraResponse comprar(CompraRequest request) {
        // Validar producto existente
        try {
            productoClient.obtenerProductoPorId(request.getProductoId());
        } catch (Exception e) {
            throw new RuntimeException("Producto no encontrado con ID: " + request.getProductoId());
        }

        // Buscar inventario
        Inventario inv = inventarioRepository.findById(request.getProductoId())
                .orElseThrow(() -> new RuntimeException("Inventario no disponible para productoId: " + request.getProductoId()));

        if (inv.getCantidad() < request.getCantidad()) {
            throw new RuntimeException("Inventario insuficiente para productoId: " + request.getProductoId());
        }

        inv.setCantidad(inv.getCantidad() - request.getCantidad());
        inventarioRepository.save(inv);

        return new CompraResponse(request.getProductoId(), request.getCantidad(), "Compra realizada exitosamente.");
    }

    private InventarioResponse mapToResponse(Inventario inv) {
        InventarioResponse response = new InventarioResponse();
        response.setProductoId(inv.getProductoId());
        response.setCantidad(inv.getCantidad());
        return response;
    }
}
