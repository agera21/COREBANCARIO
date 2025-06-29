package com.corebancario.corebancario.services;

import java.time.LocalDate;
import java.util.List;

import com.corebancario.corebancario.entities.Movimiento;

public interface MovimientoService {

    Movimiento guardar(Movimiento movimiento);

    

    List<Movimiento> listar();

    void eliminar(Long id);

    List<Movimiento> movimientosPorCuenta(Long numeroCuenta);

    List<Movimiento> reportePorClienteYFechas(Long clienteId, LocalDate desde, LocalDate hasta);

    List<Movimiento> movimientosPorCuentaYFechas(Long numeroCuenta, LocalDate desde, LocalDate hasta);
}
