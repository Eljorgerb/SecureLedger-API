package org.example.secureledgerapi.infrastructure.adapter.out.persistence.mapper;

import org.example.secureledgerapi.domain.model.Account;
import org.example.secureledgerapi.domain.model.Money;
import org.example.secureledgerapi.infrastructure.adapter.out.persistence.entity.AccountJpaEntity;
import org.springframework.stereotype.Component;

import java.util.Currency;
// Asegúrate de que todas las importaciones necesarias están aquí

@Component
public class AccountMapper {

    // --- Mapear de Dominio a JPA Entity (Necesario para Guardar/Actualizar) ---
    public AccountJpaEntity mapToJpaEntity(Account domainAccount) {
        if (domainAccount == null) return null;

        // La clase Account tiene getOwnerId(), getBalance(), getVersion()
        // El Value Object Money (obtenido con getBalance()) tiene amount() y currency()

        // **Verifica que los constructores de AccountJpaEntity coinciden con este orden**
        return new AccountJpaEntity(
                domainAccount.getId(),
                domainAccount.getOwnerId(),

                // Accede al amount del Money object
                domainAccount.getBalance().amount(), // Usar .amount() si Money es un record/inmutable

                // Accede al código de moneda del Money object
                domainAccount.getBalance().currency().getCurrencyCode(), // Usar .currency().getCurrencyCode()

                domainAccount.getVersion()
        );
    }

    // --- Mapear de JPA Entity a Dominio ---
    public Account mapToDomainEntity(AccountJpaEntity jpaEntity) {
        if (jpaEntity == null) return null;

        // **Asumimos que AccountJpaEntity tiene getBalance() y getCurrency()**

        // Crea el Value Object Money
        Money balance = new Money(
                jpaEntity.getBalance(), // Asumimos que es el BigDecimal
                Currency.getInstance(jpaEntity.getCurrency()) // Asumimos que es el String
        );

        // Crea el objeto de Dominio Account
        return new Account(
                jpaEntity.getId(),
                jpaEntity.getOwnerId(),
                balance,
                jpaEntity.getVersion()
        );
    }
}