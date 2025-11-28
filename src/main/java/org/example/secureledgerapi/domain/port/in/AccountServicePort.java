package org.example.secureledgerapi.domain.port.in;

import org.example.secureledgerapi.domain.model.Account;
import org.example.secureledgerapi.domain.model.Money;

import java.util.UUID;

// Este es el contrato que la capa de aplicación implementará.
public interface AccountServicePort {

    /**
     * Realiza una transferencia de fondos entre dos cuentas.
     * @param sourceId ID de la cuenta origen.
     * @param targetId ID de la cuenta destino.
     * @param money Monto y moneda de la transferencia.
     * @return La cuenta de origen actualizada.
     */
    Account transferFunds(UUID sourceId, UUID targetId, Money money);

    /**
     * Realiza un depósito en una cuenta.
     * @param accountId ID de la cuenta.
     * @param money Monto y moneda a depositar.
     * @return La cuenta actualizada.
     */
    Account deposit(UUID accountId, Money money);


    /**
     * Busca el saldo actual de una cuenta.
     * @param accountId ID de la cuenta.
     * @return La cuenta.
     */
    Account getAccountDetails(UUID accountId);
    Account createAccount(String ownerId, Money initialBalance);


}
