package org.example.secureledgerapi.application.port.in.command;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value // Lombok: genera constructor, getters, equals, hashCode
public class TransferFundsCommand {

    @NotNull
    UUID sourceAccountId; // Cuenta origen (la que se debita)

    @NotNull
    UUID targetAccountId; // Cuenta destino (la que se acredita)

    @NotNull
    @DecimalMin(value = "0.01", inclusive = true, message = "Amount must be positive")
    BigDecimal amount;

    // Aquí podrías añadir un campo para la moneda o la descripción, si es necesario.
}