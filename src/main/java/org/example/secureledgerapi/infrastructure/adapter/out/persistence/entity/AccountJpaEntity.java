package org.example.secureledgerapi.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accounts")
public class AccountJpaEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Column(name = "balance_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal balanceAmount;

    @Column(name = "balance_currency", nullable = false, length = 3)
    private String balanceCurrency;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    // 🔹 Constructor vacío obligatorio para JPA
    protected AccountJpaEntity() {
    }

    // 🔹 Constructor de conveniencia
    public AccountJpaEntity(
            UUID id,
            UUID ownerId,
            BigDecimal balanceAmount,
            String balanceCurrency,
            Long version
    ) {
        this.id = id;
        this.ownerId = ownerId;
        this.balanceAmount = balanceAmount;
        this.balanceCurrency = balanceCurrency;
        this.version = version;
    }

    // --- Getters y setters SOLO para JPA ---

    public UUID getId() {
        return id;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public BigDecimal getBalanceAmount() {
        return balanceAmount;
    }

    public String getBalanceCurrency() {
        return balanceCurrency;
    }

    public Long getVersion() {
        return version;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public void setBalanceAmount(BigDecimal balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public void setBalanceCurrency(String balanceCurrency) {
        this.balanceCurrency = balanceCurrency;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
