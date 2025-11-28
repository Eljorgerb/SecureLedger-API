package org.example.secureledgerapi.domain.port.out;

import org.example.secureledgerapi.domain.model.Account;
import java.util.UUID;

public interface AccountPersistencePort {

    /**
     * Carga una cuenta por su ID.
     * @param accountId ID de la cuenta.
     * @return La cuenta de dominio.
     */
    Account loadAccount(UUID accountId);

    /**
     * Persiste una cuenta (INSERT para nueva, UPDATE para existente).
     * @param account La entidad de dominio Account a guardar/actualizar.
     * @return La cuenta guardada (que contendrá el ID final si se generó en la DB).
     */
    Account saveAccount(Account account);

}