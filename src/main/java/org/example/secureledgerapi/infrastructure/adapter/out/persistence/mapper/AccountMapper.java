package org.example.secureledgerapi.infrastructure.adapter.out.persistence.mapper;

import org.example.secureledgerapi.domain.model.Account;
import org.example.secureledgerapi.domain.model.Money;
import org.example.secureledgerapi.infrastructure.adapter.out.persistence.entity.AccountJpaEntity;
import org.springframework.stereotype.Component;

import java.util.Currency;

@Component
public class AccountMapper {

    // --- Mapear de Dominio a JPA Entity ---
    public AccountJpaEntity mapToJpaEntity(Account domainAccount) {
        if (domainAccount == null) {
            return null;
        }

        return new AccountJpaEntity(
                domainAccount.getId(),
                domainAccount.getOwnerId(),
                domainAccount.getBalance().amount(),                 // BigDecimal
                domainAccount.getBalance().currency().getCurrencyCode(), // String (ISO)
                domainAccount.getVersion()
        );
    }

    // --- Mapear de JPA Entity a Dominio ---
    public Account mapToDomainEntity(AccountJpaEntity jpaEntity) {
        if (jpaEntity == null) {
            return null;
        }

        Money balance = new Money(
                jpaEntity.getBalanceAmount(),                        // BigDecimal
                Currency.getInstance(jpaEntity.getBalanceCurrency()) // Currency
        );

        return new Account(
                jpaEntity.getId(),
                jpaEntity.getOwnerId(),
                balance,
                jpaEntity.getVersion()
        );
    }
}
