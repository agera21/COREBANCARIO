package com.corebancario.corebancario.services;



import java.util.List;
import java.util.Optional;

import com.corebancario.corebancario.entities.Cliente;

public interface ClienteService {

    Cliente guardar(Cliente cliente);

        Cliente actualizar(Cliente cliente); // 🔹 Nuevo método

    Optional<Cliente> buscarPorId(Long id);

    List<Cliente> listar();

    void eliminar(Long id);

    Optional<Cliente> buscarPorIdentificacion(String identificacion);

    boolean existePorIdentificacion(String identificacion);
}

