package org.example.secureledgerapi.infrastructure.adapter.in.web;

import org.example.secureledgerapi.domain.exception.AccountNotFoundException;
import org.example.secureledgerapi.domain.model.InsufficientFundsException; // Asumiendo que crearás esta excepción en el Dominio

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 404 NOT FOUND para cuentas no encontradas
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Object> handleAccountNotFound(AccountNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Not Found", "message", ex.getMessage()));
    }

    // 400 BAD REQUEST para lógica de negocio fallida (fondos insuficientes)
    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<Object> handleInsufficientFunds(InsufficientFundsException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(Map.of("error", "Bad Request", "message", ex.getMessage()));
    }

    // Manejar errores de Optimistic Locking (Concurrencia)
    @ExceptionHandler(org.springframework.dao.OptimisticLockingFailureException.class)
    public ResponseEntity<Object> handleOptimisticLockingFailure(org.springframework.dao.OptimisticLockingFailureException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(Map.of("error", "Conflict", "message", "Transaction failed due to concurrent modification. Please retry."));
    }
}
