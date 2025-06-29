package com.corebancario.corebancario.controllers;

import com.corebancario.corebancario.entities.Cliente;
import com.corebancario.corebancario.services.ClienteService;
import com.corebancario.corebancario.utilitarios.utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private utils util;

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Cliente cliente) {
        Cliente guardado = clienteService.guardar(cliente);
        if (guardado == null) {
            return ResponseEntity
                    .badRequest()
                    .body(util.respuestaError("Error", "Es probable que el cliente ya esté registrado ó los parametros enviados son invalidos"));
        }

        return ResponseEntity.ok(util.respuestaError(
                "Correcto",
                "Se registró el Cliente: " + guardado.getPersona().getNombre() +
                        " con identificación: " + guardado.getPersona().getIdentificacion() + " exitosamente"
        ));
    }

    @PutMapping
    public ResponseEntity<?> actualizar(@RequestBody Cliente cliente) {
        Cliente actualizado = clienteService.actualizar(cliente);
        if (actualizado == null) {
            return ResponseEntity
                    .badRequest()
                    .body(util.respuestaError("Error", "No se pudo actualizar: la persona o el cliente no existen"));
        }

        return ResponseEntity.ok(util.respuestaError(
                "Correcto",
                "Se actualizó el Cliente con identificación: " + actualizado.getPersona().getIdentificacion() + " exitosamente"
        ));
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        List<Cliente> clientes = clienteService.listar();
        if (clientes.isEmpty()) {
            return ResponseEntity
                    .ok(util.respuestaError("Sin datos", "No hay clientes registrados"));
        }

        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.buscarPorId(id);
        return cliente
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .status(404)
                        .body(util.respuestaError("No encontrado", "No existe un cliente con ese ID")));
    }

    @GetMapping("/identificacion/{identificacion}")
    public ResponseEntity<?> buscarPorIdentificacion(@PathVariable String identificacion) {
        Optional<Cliente> cliente = clienteService.buscarPorIdentificacion(identificacion);
        return cliente
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity
                        .status(404)
                        .body(util.respuestaError("No encontrado", "No existe un cliente con esa identificación")));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.buscarPorId(id);
        if (cliente.isEmpty()) {
            return ResponseEntity
                    .status(404)
                    .body(util.respuestaError("No encontrado", "El cliente con ID " + id + " no existe"));
        }

        clienteService.eliminar(id);
        return ResponseEntity.ok(util.respuestaError("Correcto", "Cliente eliminado correctamente"));
    }
}
