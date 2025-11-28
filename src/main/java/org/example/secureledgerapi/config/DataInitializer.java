package org.example.secureledgerapi.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.secureledgerapi.infrastructure.adapter.out.persistence.entity.AccountJpaEntity;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class DataInitializer implements ApplicationRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {

        List<AccountJpaEntity> accounts = createTestAccounts();

        System.out.println("Inicializando base de datos con cuentas de prueba...");

        for (AccountJpaEntity account : accounts) {
            account.setVersion(null);
            entityManager.persist(account);
        }

        entityManager.flush();

        System.out.println("Cuentas de prueba cargadas correctamente.");
    }

    private List<AccountJpaEntity> createTestAccounts() {
        // Los IDs predefinidos
        UUID accountAId = UUID.fromString("1a733716-e4d0-482a-a92c-550304c4f34d");
        UUID accountBId = UUID.fromString("b2c34c56-e4d0-482a-a92c-550304c4f34d");

        // CUENTA A (Origen) - USD
        AccountJpaEntity accountA = new AccountJpaEntity(
                accountAId,
                UUID.fromString("2d7f8c14-a901-4c12-8e1d-842c67b93a0b"),
                BigDecimal.valueOf(100.00),
                "USD", // <--- MONEDA UNIFICADA
                0L
        );

        // CUENTA B (Destino) - USD
        AccountJpaEntity accountB = new AccountJpaEntity(
                accountBId,
                UUID.fromString("3e9d0a27-b012-5d23-9f2e-953d78c04b1c"),
                BigDecimal.valueOf(500.00),
                "USD", // <--- CORREGIDO: Ambas deben ser USD
                0L
        );

        return Arrays.asList(accountA, accountB);
    }
}