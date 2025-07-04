package com.corebancario.corebancario.repositories;

import com.corebancario.corebancario.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByPersonaIdentificacion(String identificacion);

    boolean existsByPersonaIdentificacion(String identificacion);
}

