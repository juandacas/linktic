package com.mitienda.inventario_service.controller;

import com.mitienda.inventario_service.dto.CompraRequest;
import com.mitienda.inventario_service.dto.CompraResponse;
import com.mitienda.inventario_service.dto.InventarioRequest;
import com.mitienda.inventario_service.dto.InventarioResponse;
import com.mitienda.inventario_service.service.CompraService;
import com.mitienda.inventario_service.service.InventarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    private final InventarioService inventarioService;
    private final CompraService compraService;

    public InventarioController(InventarioService inventarioService, CompraService compraService ) {
        this.inventarioService = inventarioService;
        this.compraService = compraService;
    }

    @PostMapping
    public ResponseEntity<InventarioResponse> crear(@RequestBody InventarioRequest request) {
        InventarioResponse response = inventarioService.crearInventario(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{productoId}")
    public ResponseEntity<InventarioResponse> consultar(@PathVariable Long productoId) {
        return ResponseEntity.ok(inventarioService.obtenerInventario(productoId));
    }

    @PutMapping("/{productoId}")
    public ResponseEntity<InventarioResponse> actualizar(@PathVariable Long productoId,
                                                         @RequestBody InventarioRequest request) {
        return ResponseEntity.ok(inventarioService.actualizarInventario(productoId, request));
    }

    @PostMapping("/compras")
    public ResponseEntity<CompraResponse> realizarCompra(@RequestBody CompraRequest request) {
        CompraResponse response = compraService.realizarCompra(request);
        return ResponseEntity.ok(response);
    }
}
