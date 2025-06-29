package com.corebancario.corebancario.utilitarios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class utils {

        private  static final ObjectMapper objectMapper = new ObjectMapper();
 public String respuestaError(String tipo, String mensaje) {
        try {
            Map<String, String> error = new HashMap<>();
            error.put("tipo", tipo);
            error.put("mensaje", mensaje);

            // Retorna el JSON con formato bonito
            return objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValueAsString(error);

        } catch (JsonProcessingException e) {
            return """
                   {
                     "tipo": "Error",
                     "mensaje": "Error interno generando respuesta"
                   }
                   """;
        }
    }

}
