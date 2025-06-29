package com.corebancario.corebancario.Handler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleJsonMalFormado(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(respuestaError("Error JSON", "El formato del JSON es incorrecto o hay datos inválidos."));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidacionFallida(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(respuestaError("Validación", "Faltan propiedades obligatorias o hay datos inválidos."));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<?> handleNoHandler(NoHandlerFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuestaError("Ruta incorrecta", "El endpoint solicitado no existe."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOtrosErrores(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuestaError("Error interno", ex.getMessage()));
    }

    private Map<String, String> respuestaError(String estado, String mensaje) {
        Map<String, String> respuesta = new HashMap<>();
        respuesta.put("estado", estado);
        respuesta.put("mensaje", mensaje);
        return respuesta;
    }
}
