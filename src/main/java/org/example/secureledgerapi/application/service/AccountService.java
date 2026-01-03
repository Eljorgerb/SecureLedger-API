package org.example.secureledgerapi.application.service;

import org.example.secureledgerapi.application.port.out.LoadAccountPort;
import org.example.secureledgerapi.application.port.out.UpdateAccountStatePort;
import org.example.secureledgerapi.domain.exception.AccountNotFoundException;
import org.example.secureledgerapi.domain.model.Account;
import org.example.secureledgerapi.domain.model.Money;
import org.example.secureledgerapi.domain.port.in.AccountServicePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AccountService implements AccountServicePort {

    private final LoadAccountPort loadAccountPort;
    private final UpdateAccountStatePort updateAccountStatePort;

    public AccountService(
            LoadAccountPort loadAccountPort,
            UpdateAccountStatePort updateAccountStatePort
    ) {
        this.loadAccountPort = loadAccountPort;
        this.updateAccountStatePort = updateAccountStatePort;
    }

    // --- 1. Transferir fondos ---
    @Override
    @Transactional
    public Account transferFunds(UUID sourceId, UUID targetId, Money money) {

        Account sourceAccount = loadAccountPort.load(sourceId)
                .orElseThrow(() ->
                        new AccountNotFoundException("Cuenta origen no encontrada: " + sourceId)
                );

        Account targetAccount = loadAccountPort.load(targetId)
                .orElseThrow(() ->
                        new AccountNotFoundException("Cuenta destino no encontrada: " + targetId)
                );

        sourceAccount.withdraw(money);
        targetAccount.deposit(money);

        updateAccountStatePort.update(targetAccount);
        return updateAccountStatePort.update(sourceAccount);
    }

    // --- 2. Depósito ---
    @Override
    @Transactional
    public Account deposit(UUID accountId, Money money) {

        Account account = loadAccountPort.load(accountId)
                .orElseThrow(() ->
                        new AccountNotFoundException("Cuenta no encontrada para depósito: " + accountId)
                );

        account.deposit(money);
        return updateAccountStatePort.update(account);
    }

    // --- 3. Obtener detalles de cuenta ---
    @Override
    public Account getAccountDetails(UUID accountId) {
        return loadAccountPort.load(accountId)
                .orElseThrow(() ->
                        new AccountNotFoundException("Cuenta no encontrada: " + accountId)
                );
    }

    // --- 4. Crear cuenta ---
    @Override
    @Transactional
    public Account createAccount(String ownerId, Money initialBalance) {

        UUID accountId = UUID.randomUUID();
        UUID ownerUuid = UUID.fromString(ownerId);

        Account newAccount = new Account(
                accountId,
                ownerUuid,
                initialBalance,
                0L
        );

        return updateAccountStatePort.update(newAccount);
    }
}
