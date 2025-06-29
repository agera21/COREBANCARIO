package com.corebancario.corebancario.services;

import com.corebancario.corebancario.entities.Cliente;
import com.corebancario.corebancario.entities.Persona;
import com.corebancario.corebancario.repositories.ClienteRepository;
import com.corebancario.corebancario.repositories.PersonaRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private PersonaRepository personaRepository;

    @Transactional
@Override
public Cliente guardar(Cliente cliente) {
    Persona persona = cliente.getPersona();

    // Validar que la identificación no esté ya registrada en cliente
    if (persona != null && persona.getIdentificacion() != null) {
        if (clienteRepository.existsByPersonaIdentificacion(persona.getIdentificacion())) {
            return null; // Ya hay un cliente con esa identificación
        }

        // Buscar persona por identificación
        Optional<Persona> personaExistente = personaRepository.findByIdentificacion(persona.getIdentificacion());

        Persona personaFinal;
        if (personaExistente.isPresent()) {
            personaFinal = personaExistente.get();
        } else {
            // Guardar nueva persona
            personaFinal = personaRepository.save(persona);
        }

        // Asociar y guardar cliente
        cliente.setPersona(personaFinal);
        return clienteRepository.save(cliente);
    }

    return null; // No se puede continuar sin identificación válida
}

@Override
public Cliente actualizar(Cliente cliente) {
    String identificacion = cliente.getPersona().getIdentificacion();

    Optional<Persona> personaOptional = personaRepository.findByIdentificacion(identificacion);
    if (personaOptional.isEmpty()) return null;

    Persona personaExistente = personaOptional.get();

    Optional<Cliente> clienteOptional = clienteRepository.findByPersonaIdentificacion(identificacion);
    if (clienteOptional.isEmpty()) return null;

    Cliente clienteExistente = clienteOptional.get();

    // Actualizar datos de persona
    Persona nuevaPersona = cliente.getPersona();
    personaExistente.setNombre(nuevaPersona.getNombre());
    personaExistente.setGenero(nuevaPersona.getGenero());
    personaExistente.setEdad(nuevaPersona.getEdad());
    personaExistente.setDireccion(nuevaPersona.getDireccion());
    personaExistente.setTelefono(nuevaPersona.getTelefono());
    personaRepository.save(personaExistente);

    // Actualizar datos de cliente
    clienteExistente.setContrasena(cliente.getContrasena());
    clienteExistente.setEstado(cliente.getEstado());

    return clienteRepository.save(clienteExistente);
}



    @Override
    public Optional<Cliente> buscarPorIdentificacion(String identificacion) {
        return clienteRepository.findByPersonaIdentificacion(identificacion);
    }

    @Override
    public boolean existePorIdentificacion(String identificacion) {
        return clienteRepository.existsByPersonaIdentificacion(identificacion);
    }

    @Override
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    @Override
    public void eliminar(Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
        }
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
              return clienteRepository.findById(id);
    }
}
