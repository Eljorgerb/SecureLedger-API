package org.example.secureledgerapi.domain.exception;

// Usamos RuntimeException, ya que no son errores recuperables en el punto de llamada
public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String message) {
        super(message);
    }

    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}