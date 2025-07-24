package com.mitienda.productos_service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponse {
    private Long id;
    private String nombre;
    private Double precio;
    private String descripcion;
}
