package com.corebancario.corebancario.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.corebancario.corebancario.entities.Movimiento;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {

    List<Movimiento> findByCuentaNumeroCuenta(Long numeroCuenta);

    List<Movimiento> findByCuentaClienteId(Long clienteId);

    List<Movimiento> findByCuentaClienteIdAndFechaBetween(
        Long clienteId, LocalDate fechaInicio, LocalDate fechaFin
    );

    List<Movimiento> findByCuentaNumeroCuentaAndFechaBetween(
        Long numeroCuenta, LocalDate fechaInicio, LocalDate fechaFin
    );

    void deleteByCuentaNumeroCuenta(Long numeroCuenta);
}

