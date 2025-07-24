package com.mitienda.inventario_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioRequest {
    private Long productoId;
    private Integer cantidad;
}
