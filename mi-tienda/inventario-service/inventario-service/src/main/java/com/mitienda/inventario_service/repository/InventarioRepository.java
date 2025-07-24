package com.mitienda.inventario_service.repository;

import com.mitienda.inventario_service.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventarioRepository extends JpaRepository<Inventario, Long> {

    Inventario findByProductoId(Long productoId);

}
