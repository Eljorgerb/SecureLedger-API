package org.example.secureledgerapi.application.service;

import org.example.secureledgerapi.domain.model.Account;
import org.example.secureledgerapi.domain.model.Money;
import org.example.secureledgerapi.domain.port.in.AccountServicePort;
import org.example.secureledgerapi.domain.port.out.AccountPersistencePort;
import org.example.secureledgerapi.domain.exception.AccountNotFoundException; // Asegúrate de tener esta excepción
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AccountService implements AccountServicePort {

    private final AccountPersistencePort accountPersistencePort;

    public AccountService(AccountPersistencePort accountPersistencePort) {
        this.accountPersistencePort = accountPersistencePort;
    }

    // --- 1. Método transferFunds (Existente) ---
    @Override
    @Transactional
    public Account transferFunds(UUID sourceId, UUID targetId, Money money) {
        // Lógica de transferencia: cargar A, debitar, cargar B, depositar, guardar A, guardar B.
        Account sourceAccount = accountPersistencePort.loadAccount(sourceId);
        if (sourceAccount == null) {
            throw new AccountNotFoundException("Cuenta origen no encontrada: " + sourceId);
        }

        Account targetAccount = accountPersistencePort.loadAccount(targetId);
        if (targetAccount == null) {
            throw new AccountNotFoundException("Cuenta destino no encontrada: " + targetId);
        }

        sourceAccount.withdraw(money);
        targetAccount.deposit(money);

        accountPersistencePort.saveAccount(targetAccount);
        return accountPersistencePort.saveAccount(sourceAccount);
    }

    // --- 2. Método deposit (NUEVA IMPLEMENTACIÓN) ---
    @Override
    @Transactional
    public Account deposit(UUID accountId, Money money) {
        Account account = accountPersistencePort.loadAccount(accountId);
        if (account == null) {
            throw new AccountNotFoundException("Cuenta no encontrada para depósito: " + accountId);
        }

        account.deposit(money);
        return accountPersistencePort.saveAccount(account);
    }

    // --- 3. Método getAccountDetails (Existente) ---
    @Override
    public Account getAccountDetails(UUID accountId) {
        return accountPersistencePort.loadAccount(accountId);
    }

    // --- 4. Método createAccount (NUEVA IMPLEMENTACIÓN) ---
    @Override
    @Transactional
    public Account createAccount(String ownerId, Money initialBalance) {

        // 1. Lógica de Dominio: Crea una nueva cuenta (con un nuevo ID y versión 0).
        UUID accountId = UUID.randomUUID();
        UUID ownerUuid = UUID.fromString(ownerId);

        // Asumiendo que el constructor de Account toma (UUID id, UUID owner, Money balance, Long version)
        Account newAccount = new Account(accountId, ownerUuid, initialBalance, 0L);

        // 2. Llama al Puerto de Salida para persistir la entidad.
        return accountPersistencePort.saveAccount(newAccount);
    }
}