package com.mitienda.productos_service.service;


import com.mitienda.productos_service.dto.ProductoRequest;
import com.mitienda.productos_service.dto.ProductoResponse;
import com.mitienda.productos_service.model.Producto;
import com.mitienda.productos_service.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public ProductoResponse crearProducto(ProductoRequest request) {
        Producto producto = Producto.builder()
                .nombre(request.getNombre())
                .precio(request.getPrecio())
                .descripcion(request.getDescripcion())
                .build();

        Producto guardado = productoRepository.save(producto);

        return new ProductoResponse(guardado.getId(), guardado.getNombre(), guardado.getPrecio(), guardado.getDescripcion());
    }

    public Optional<ProductoResponse> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id)
                .map(p -> new ProductoResponse(p.getId(), p.getNombre(), p.getPrecio(), p.getDescripcion()));
    }

    public List<ProductoResponse> listarTodos() {
        return productoRepository.findAll().stream()
                .map(p -> new ProductoResponse(p.getId(), p.getNombre(), p.getPrecio(), p.getDescripcion()))
                .toList();
    }
}
