package com.corebancario.corebancario.repositories;

import com.corebancario.corebancario.entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {

    boolean existsByIdentificacion(String identificacion);

    Optional<Persona> findByIdentificacion(String identificacion);
}
