package com.mitienda.inventario_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraResponse {
    private Long productoId;
    private Integer cantidadComprada;
    private String mensaje;

    public CompraResponse(String mensaje) {
        this.mensaje = mensaje;
    }
}
