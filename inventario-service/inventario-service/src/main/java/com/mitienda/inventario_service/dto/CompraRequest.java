package com.mitienda.inventario_service.dto;

import lombok.Data;

@Data
public class CompraRequest {
    private Long productoId;
    private Integer cantidad;
}
