package org.example.secureledgerapi.infrastructure.adapter.out.persistence;

import org.example.secureledgerapi.domain.model.Account;
import org.example.secureledgerapi.domain.port.out.AccountPersistencePort;
import org.example.secureledgerapi.infrastructure.adapter.out.persistence.mapper.AccountMapper; // Importación clave
import org.example.secureledgerapi.infrastructure.adapter.out.persistence.entity.AccountJpaEntity;

import org.springframework.stereotype.Component;

@Component
public class AccountPersistenceAdapter implements AccountPersistencePort {

    private final SpringDataAccountRepository accountRepository; // Asume el repositorio de JPA
    private final AccountMapper accountMapper;

    // Constructor: Ahora puede inyectar AccountMapper sin error
    public AccountPersistenceAdapter(SpringDataAccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public Account loadAccount(java.util.UUID accountId) {
        // Lógica de carga...
        return accountRepository.findById(accountId)
                .map(accountMapper::mapToDomainEntity)
                .orElse(null); // O lanzar una excepción
    }

    @Override
    public Account saveAccount(Account account) {
        // 1. Usa el Mapper
        AccountJpaEntity entity = accountMapper.mapToJpaEntity(account);

        // 2. Persiste
        AccountJpaEntity savedEntity = accountRepository.save(entity);

        // 3. Usa el Mapper para devolver el objeto de Dominio
        return accountMapper.mapToDomainEntity(savedEntity);
    }
}