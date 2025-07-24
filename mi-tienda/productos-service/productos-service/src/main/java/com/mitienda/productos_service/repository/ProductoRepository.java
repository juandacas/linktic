package com.mitienda.productos_service.repository;

import com.mitienda.productos_service.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
