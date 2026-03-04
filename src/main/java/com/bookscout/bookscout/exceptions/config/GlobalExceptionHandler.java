package com.bookscout.bookscout.exceptions.config;
import com.bookscout.bookscout.exceptions.ValidacionDeIdentidad;
import com.bookscout.bookscout.exceptions.dto.ErrorResponseDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Manejo de tus errores de lógica (ValidacionDeIdentidad)
    @ExceptionHandler(ValidacionDeIdentidad.class)
    public ResponseEntity<ErrorResponseDTO> handleBusinessException(ValidacionDeIdentidad ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                ex.getMessage(),
                null,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); // Devuelve 404
    }

    // 2. Manejo de errores de @Valid (MethodArgumentNotValidException)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationErrors(MethodArgumentNotValidException ex) {

        // Extraemos todos los mensajes de error de las anotaciones (ej: "El precio es obligatorio")
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "Error en la validación de datos",
                errors,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 3. Manejo de errores generales (Cualquier otra cosa que no esperábamos)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGlobalException(Exception ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                "Ocurrió un error interno en el servidor",
                List.of(ex.getMessage()),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR); // Devuelve 500
    }
}