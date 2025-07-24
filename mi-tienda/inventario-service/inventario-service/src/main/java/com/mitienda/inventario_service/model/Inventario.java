package com.mitienda.inventario_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventario {
    @Id
    private Long productoId; // Relación con Producto

    @Column(nullable = false)
    private Integer cantidad;
}
