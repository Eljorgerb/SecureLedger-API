package org.example.secureledgerapi.domain.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Currency;

// Usando Java 17 Record para inmutabilidad y concisión
public record Money(BigDecimal amount, Currency currency) {

    // Constructor compacto con validación para Clean Code
    public Money {
        if (amount == null || currency == null) {
            throw new IllegalArgumentException("Amount and Currency must not be null.");
        }
        // Aseguramos que el dinero nunca tiene más de 2 decimales
        if (amount.scale() > 2) {
            throw new IllegalArgumentException("Money amount cannot have more than two decimal places.");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Money amount cannot be negative.");
        }
    }

    // Método para crear nuevas instancias de Money para sumas
    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Cannot add different currencies.");
        }
        return new Money(this.amount.add(other.amount), this.currency);
    }

    // Método para crear nuevas instancias de Money para restas
    public Money subtract(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new IllegalArgumentException("Cannot subtract different currencies.");
        }
        return new Money(this.amount.subtract(other.amount), this.currency);
    }

}