package org.example.secureledgerapi.domain.model;

import lombok.Data;

import java.util.UUID;
@Data
public class Account {

    private final UUID id;
    private final UUID ownerId;
    private Money balance;
    private Long version; // Clave para Optimistic Locking

    // Constructor con todos los campos
    public Account(UUID id, UUID ownerId, Money balance, Long version) {
        this.id = id;
        this.ownerId = ownerId;
        this.balance = balance;
        this.version = version;
    }

    // --- MÉTODOS DE DOMINIO (LÓGICA DE NEGOCIO EN LA ENTIDAD) ---

    public void deposit(Money amount) {
        if (!this.balance.currency().equals(amount.currency())) {
            throw new IllegalArgumentException("Currency mismatch for deposit.");
        }
        this.balance = this.balance.add(amount);
    }

    public void withdraw(Money amount) {
        if (!this.balance.currency().equals(amount.currency())) {
            throw new IllegalArgumentException("Currency mismatch for withdrawal.");
        }
        if (this.balance.amount().compareTo(amount.amount()) < 0) {
            // Creamos una excepción de Dominio, no una Runtime Exception genérica.
            throw new InsufficientFundsException("Account " + id + " has insufficient funds.");
        }
        this.balance = this.balance.subtract(amount);
    }

    // Incrementa la versión para el bloqueo optimista
    public void markModified() {
        this.version++;
    }

    // --- Getters (Los setters están controlados por los métodos de dominio) ---

    public UUID getId() { return id; }
    public UUID getOwnerId() { return ownerId; }
    public Money getBalance() { return balance; }
    public Long getVersion() { return version; }

}