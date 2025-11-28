package org.example.secureledgerapi.domain.model;

// Esta excepción se lanza desde la entidad Account cuando la lógica de negocio falla.
// La capa de infraestructura (ExceptionHandler) la capturará y devolverá un HTTP 400.
public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(String message) {
        super(message);
    }
}
