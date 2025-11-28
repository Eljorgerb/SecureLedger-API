package org.example.secureledgerapi.application.port.out;

import org.example.secureledgerapi.infrastructure.adapter.out.persistence.entity.AccountJpaEntity;
import java.util.UUID;

// Carga la entidad para que la lógica de negocio pueda trabajar con ella.
public interface LoadAccountPort {

    AccountJpaEntity loadAccount(UUID accountId);
}