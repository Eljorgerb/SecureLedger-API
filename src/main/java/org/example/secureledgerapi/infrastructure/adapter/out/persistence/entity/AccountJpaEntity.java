package org.example.secureledgerapi.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

// Usamos Lombok aquí para reducir el boilerplate (Getters/Setters)
@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountJpaEntity {

    @Id
    private UUID id;

    private UUID ownerId;

    // Persistimos el balance y la moneda como columnas separadas
    private BigDecimal balanceAmount;

    private String balanceCurrency;

    @Version
    // CRUCIAL: Esta anotación de JPA es la que permite el Optimistic Locking
    // Spring/Hibernate se encargará de gestionar este campo automáticamente
    private Long version;


}