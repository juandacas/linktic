package com.mitienda.productos_service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequest {
    private String nombre;
    private Double precio;
    private String descripcion;
}
