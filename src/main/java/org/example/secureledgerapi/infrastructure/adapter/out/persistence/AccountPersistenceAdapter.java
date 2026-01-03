package org.example.secureledgerapi.infrastructure.adapter.out.persistence;

import org.example.secureledgerapi.application.port.out.LoadAccountPort;
import org.example.secureledgerapi.application.port.out.UpdateAccountStatePort;
import org.example.secureledgerapi.domain.model.Account;
import org.example.secureledgerapi.infrastructure.adapter.out.persistence.mapper.AccountMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class AccountPersistenceAdapter
        implements LoadAccountPort, UpdateAccountStatePort {

    private final SpringDataAccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountPersistenceAdapter(
            SpringDataAccountRepository accountRepository,
            AccountMapper accountMapper
    ) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public Optional<Account> load(UUID accountId) {
        return accountRepository.findById(accountId)
                .map(accountMapper::mapToDomainEntity);
    }

    @Override
    public Account update(Account account) {
        var savedEntity =
                accountRepository.save(accountMapper.mapToJpaEntity(account));
        return accountMapper.mapToDomainEntity(savedEntity);
    }
}
