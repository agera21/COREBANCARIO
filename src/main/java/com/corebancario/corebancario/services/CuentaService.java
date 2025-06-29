package com.corebancario.corebancario.services;

import com.corebancario.corebancario.entities.Cuenta;

import java.util.List;
import java.util.Optional;

public interface CuentaService {

    Cuenta guardar(Cuenta cuenta);

    Cuenta actualizar(Cuenta cuenta);

    Optional<Cuenta> buscarPorNumero(Long numeroCuenta);

    List<Cuenta> listar();

    Cuenta eliminar(Long numeroCuenta);

    List<Cuenta> listarPorCliente(Long clienteId);
}
