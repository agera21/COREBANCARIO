package com.corebancario.corebancario.controllers;

import com.corebancario.corebancario.entities.Cuenta;
import com.corebancario.corebancario.services.CuentaService;
import com.corebancario.corebancario.utilitarios.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @Autowired
    private utils util;

    // Guardar cuenta (POST)
    @PostMapping
    public ResponseEntity<?> guardarCuenta(@RequestBody Cuenta cuenta) {
        Cuenta guardada = cuentaService.guardar(cuenta);
        if (guardada == null) {
            return ResponseEntity.badRequest().body(
                    util.respuestaError("Error", "No se pudo guardar la cuenta. Verifique el número de cuenta, la identificación del cliente ó los parametros enviados son invalidos."));
        }
        return ResponseEntity.ok(util.respuestaError("Correcto", "Cuenta número: " + guardada.getNumeroCuenta() + " registrada exitosamente"));
    }

    // Actualizar cuenta (PUT)
    @PutMapping
    public ResponseEntity<?> actualizarCuenta(@RequestBody Cuenta cuenta) {
        Cuenta actualizada = cuentaService.actualizar(cuenta);
        if (actualizada == null) {
            return ResponseEntity.badRequest().body(
                    util.respuestaError("Error", "No se pudo actualizar la cuenta. Verifique los datos."));
        }
        return ResponseEntity.ok(util.respuestaError("Correcto", "Cuenta número: " + actualizada.getNumeroCuenta() + " actualizada exitosamente"));
    }

    // Obtener cuenta por número
    @GetMapping("/{numeroCuenta}")
    public ResponseEntity<?> obtenerPorNumero(@PathVariable Long numeroCuenta) {
        Optional<Cuenta> cuenta = cuentaService.buscarPorNumero(numeroCuenta);
        return cuenta
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .status(404)
                        .body(util.respuestaError("No encontrado", "Cuenta no encontrada con el número: " + numeroCuenta)));
    }

    // Listar todas las cuentas
    @GetMapping
    public ResponseEntity<?> listarCuentas() {
        List<Cuenta> cuentas = cuentaService.listar();
        if (cuentas.isEmpty()) {
            return ResponseEntity.ok(util.respuestaError("Sin datos", "No hay cuentas registradas"));
        }
        return ResponseEntity.ok(cuentas);
    }

    // Eliminar cuenta por número
    @DeleteMapping("/{numeroCuenta}")
    public ResponseEntity<?> eliminarCuenta(@PathVariable Long numeroCuenta) {
        Optional<Cuenta> cuenta = cuentaService.buscarPorNumero(numeroCuenta);
        if (cuenta.isEmpty()) {
            return ResponseEntity.status(404).body(util.respuestaError("No encontrado", "La cuenta no existe"));
        }

        cuentaService.eliminar(numeroCuenta);
        return ResponseEntity.ok(util.respuestaError("Correcto", "Cuenta eliminada correctamente"));
    }

    // Listar cuentas por cliente ID
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<?> listarPorCliente(@PathVariable Long clienteId) {
        List<Cuenta> cuentas = cuentaService.listarPorCliente(clienteId);
        if (cuentas.isEmpty()) {
            return ResponseEntity.ok(util.respuestaError("Sin datos", "Este cliente no tiene cuentas asociadas"));
        }
        return ResponseEntity.ok(cuentas);
    }
}
