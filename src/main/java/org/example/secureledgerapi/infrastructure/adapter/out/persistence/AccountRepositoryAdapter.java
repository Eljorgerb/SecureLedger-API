package org.example.secureledgerapi.infrastructure.adapter.out.persistence;

import org.example.secureledgerapi.domain.model.Account;
import org.example.secureledgerapi.domain.port.out.AccountRepositoryPort;
import org.example.secureledgerapi.infrastructure.adapter.out.persistence.entity.AccountJpaEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;
import java.util.UUID;

// Este componente es el Adaptador que implementa el contrato de Dominio (Puerto de Salida)
@Component
public class AccountRepositoryAdapter implements AccountRepositoryPort {

    private final SpringDataAccountRepository jpaRepository;

    // NOTA: Aquí inyectaríamos el MapStruct Mapper, por ahora lo hacemos a mano para demostrar el concepto.
    public AccountRepositoryAdapter(SpringDataAccountRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    // --- Mapeo de Entidad JPA a Modelo de Dominio ---
    private Account mapToDomainEntity(AccountJpaEntity entity) {
        return new Account(
                entity.getId(),
                entity.getOwnerId(),
                // Reconstruimos el Value Object Money
                new org.example.secureledgerapi.domain.model.Money(
                        entity.getBalanceAmount(),
                        Currency.getInstance(entity.getBalanceCurrency())
                ),
                entity.getVersion()
        );
    }

    // --- Mapeo de Modelo de Dominio a Entidad JPA ---
    private AccountJpaEntity mapToJpaEntity(Account domain) {
        return new AccountJpaEntity(
                domain.getId(),
                domain.getOwnerId(),
                domain.getBalance().amount(),
                domain.getBalance().currency().getCurrencyCode(),
                domain.getVersion()
        );
    }

    @Override
    public Optional<Account> load(UUID id) {
        // Usamos Spring Data JPA para buscar y mapeamos el resultado
        return jpaRepository.findById(id)
                .map(this::mapToDomainEntity);
    }

    @Override
    public Account update(Account account) {
        // Mapeamos a JPA, guardamos, y devolvemos la versión del Dominio.
        AccountJpaEntity jpaEntity = mapToJpaEntity(account);
        AccountJpaEntity savedEntity = jpaRepository.save(jpaEntity);
        return mapToDomainEntity(savedEntity);
    }
}
