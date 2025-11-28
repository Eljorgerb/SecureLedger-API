package org.example.secureledgerapi.domain.port.out;

import org.example.secureledgerapi.domain.model.Account;
import java.util.Optional;
import java.util.UUID;

// Este es el contrato que la capa de infraestructura DEBE implementar.
public interface AccountRepositoryPort {

    /**
     * Carga una cuenta por su ID.
     * @param id El ID de la cuenta.
     * @return Un objeto Optional que contiene la cuenta si existe.
     */
    Optional<Account> load(UUID id);

    /**
     * Actualiza o guarda una cuenta en el sistema.
     * @param account La entidad de dominio Account a persistir.
     * @return La cuenta persistida.
     */
    Account update(Account account);
}
