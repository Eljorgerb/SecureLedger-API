package org.example.secureledgerapi.application.port.out;

import org.example.secureledgerapi.infrastructure.adapter.out.persistence.entity.AccountJpaEntity;

// Persiste los cambios de la entidad al final de la transacción.
public interface UpdateAccountStatePort {

    void updateAccount(AccountJpaEntity account);
}