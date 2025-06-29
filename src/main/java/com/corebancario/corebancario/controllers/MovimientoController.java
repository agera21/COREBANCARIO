package com.corebancario.corebancario.controllers;

import com.corebancario.corebancario.entities.Movimiento;
import com.corebancario.corebancario.services.MovimientoService;
import com.corebancario.corebancario.utilitarios.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    @Autowired
    private MovimientoService movimientoService;

    @Autowired
    private utils util;

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Movimiento movimiento) {
        Movimiento resultado = movimientoService.guardar(movimiento);
        if (resultado == null) {
            return ResponseEntity.badRequest().body(
                    util.respuestaError("Error", "No se pudo realizar la transacción. Verifique tipo, valor, saldo disponible ó los parametros enviados son invalidos."));
        }

        return ResponseEntity.ok(util.respuestaError("Correcto",
                "Se realizó una transacción de: " + resultado.getTipoMovimiento()
                        + " que afectó la Cuenta Número: " + resultado.getCuenta().getNumeroCuenta()));
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        List<Movimiento> movimientos = movimientoService.listar();
        if (movimientos.isEmpty()) {
            return ResponseEntity.ok(util.respuestaError("Sin datos", "No hay movimientos registrados"));
        }
        return ResponseEntity.ok(movimientos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            movimientoService.eliminar(id);
            return ResponseEntity.ok(util.respuestaError("Correcto", "Movimiento eliminado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(util.respuestaError("Error", "No se pudo eliminar el movimiento"));
        }
    }

    @GetMapping("/cuenta/{numeroCuenta}")
    public ResponseEntity<?> movimientosPorCuenta(@PathVariable Long numeroCuenta) {
        List<Movimiento> movimientos = movimientoService.movimientosPorCuenta(numeroCuenta);
        if (movimientos.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(util.respuestaError("Sin resultados", "No hay movimientos para esta cuenta"));
        }
        return ResponseEntity.ok(movimientos);
    }

    @PostMapping("/cliente")
    public ResponseEntity<?> reportePorClienteYFechas(@RequestBody Map<String, String> filtros) {
        try {
            Long clienteId = Long.parseLong(filtros.get("clienteId"));
            LocalDate desde = LocalDate.parse(filtros.get("desde"));
            LocalDate hasta = LocalDate.parse(filtros.get("hasta"));

            if (desde.isAfter(hasta)) {
                return ResponseEntity.badRequest()
                        .body(util.respuestaError("Validación", "La fecha 'desde' no puede ser posterior a 'hasta'"));
            }

            var movimientos = movimientoService.reportePorClienteYFechas(clienteId, desde, hasta);
            if (movimientos.isEmpty()) {
                return ResponseEntity.status(404)
                        .body(util.respuestaError("Sin resultados", "No hay movimientos para el cliente en ese rango de fechas"));
            }

            return ResponseEntity.ok(movimientos);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(util.respuestaError("Error", "Parámetros inválidos o incompletos"));
        }
    }

    @PostMapping("/cuenta/fecha")
    public ResponseEntity<?> movimientosPorCuentaYFechas(@RequestBody Map<String, String> filtros) {
        try {
            Long numeroCuenta = Long.parseLong(filtros.get("numeroCuenta"));
            LocalDate desde = LocalDate.parse(filtros.get("desde"));
            LocalDate hasta = LocalDate.parse(filtros.get("hasta"));

            if (desde.isAfter(hasta)) {
                return ResponseEntity.badRequest()
                        .body(util.respuestaError("Validación", "La fecha 'desde' no puede ser posterior a 'hasta'"));
            }

            var movimientos = movimientoService.movimientosPorCuentaYFechas(numeroCuenta, desde, hasta);
            if (movimientos.isEmpty()) {
                return ResponseEntity.status(404)
                        .body(util.respuestaError("Sin resultados", "No hay movimientos para la cuenta en ese rango de fechas"));
            }

            return ResponseEntity.ok(movimientos);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(util.respuestaError("Error", "Parámetros inválidos o incompletos"));
        }
    }
}
