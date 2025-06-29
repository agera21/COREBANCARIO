package com.corebancario.corebancario.ControllersTest;

import com.corebancario.corebancario.entities.Cliente;
import com.corebancario.corebancario.entities.Persona;
import com.corebancario.corebancario.repositories.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void guardarTest() throws Exception {
        // Usa una identificación única para evitar conflicto en la base de datos real
        String identificacion = "0801199915371"; // cámbiala cada vez si es necesario

        // Crear entidad Persona
        Persona persona = new Persona();
        persona.setNombre("Alex Gamez");
        persona.setGenero("Masculino");
        persona.setEdad(30);
        persona.setIdentificacion(identificacion);
        persona.setDireccion("Barrio El Centro");
        persona.setTelefono("99887766");

        // Crear entidad Cliente
        Cliente cliente = new Cliente();
        cliente.setPersona(persona);
        cliente.setContrasena("claveSegura");
        cliente.setEstado(true);

        // Enviar POST por primera vez
        MockHttpServletResponse response1 = mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andReturn()
                .getResponse();

        // Verifica que devuelve 200 OK
        assertEquals(200, response1.getStatus(), "Debe devolver 200 al guardar cliente nuevo");

        // Enviar POST otra vez con la misma identificación
        MockHttpServletResponse response2 = mockMvc.perform(post("/api/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andReturn()
                .getResponse();

        // Verifica que devuelve 400 Bad Request si ya existe
        assertEquals(400, response2.getStatus(), "Debe devolver 400 si el cliente ya existe");
    }
}
