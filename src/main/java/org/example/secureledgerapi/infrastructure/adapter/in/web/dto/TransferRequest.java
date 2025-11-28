package org.example.secureledgerapi.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data; // o @Value si usas Lombok

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class TransferRequest {

    @NotNull(message = "Target Account ID must be provided")
    private UUID targetAccountId;

    @NotNull(message = "Amount must be provided")
    @DecimalMin(value = "0.01", inclusive = true, message = "Amount must be positive")
    private BigDecimal amount;

    // La validación que está fallando:
    @NotBlank(message = "Currency code must be provided")
    private String currencyCode;
}