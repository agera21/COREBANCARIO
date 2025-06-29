package com.corebancario.corebancario.repositories;

import com.corebancario.corebancario.entities.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    List<Cuenta> findByClienteId(Long clienteId);

    Optional<Cuenta> findByNumeroCuenta(Long numeroCuenta);
    
    Optional<Cuenta> deleteByNumeroCuenta(Long numeroCuenta);
    

    boolean existsByNumeroCuenta(Long numeroCuenta);
}

