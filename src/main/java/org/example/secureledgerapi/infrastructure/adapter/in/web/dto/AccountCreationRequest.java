package org.example.secureledgerapi.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.util.UUID;

public class AccountCreationRequest {

    @NotBlank(message = "Owner ID must not be blank")
    private String ownerId;

    @NotNull(message = "Initial amount must be provided")
    @PositiveOrZero(message = "Initial amount cannot be negative")
    private BigDecimal initialAmount;

    @NotBlank(message = "Currency code must not be blank")
    private String currencyCode;

    // --- Getters y Setters ---

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public BigDecimal getInitialAmount() {
        return initialAmount;
    }

    public void setInitialAmount(BigDecimal initialAmount) {
        this.initialAmount = initialAmount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}