package org.example.secureledgerapi.application.port.in.command;

import org.example.secureledgerapi.domain.model.Money;

import java.util.UUID;

public class TransferFundsCommand {

    private final UUID sourceAccountId;
    private final UUID targetAccountId;
    private final Money money;

    public TransferFundsCommand(
            UUID sourceAccountId,
            UUID targetAccountId,
            Money money
    ) {
        this.sourceAccountId = sourceAccountId;
        this.targetAccountId = targetAccountId;
        this.money = money;
    }

    public UUID getSourceAccountId() {
        return sourceAccountId;
    }

    public UUID getTargetAccountId() {
        return targetAccountId;
    }

    public Money getMoney() {
        return money;
    }
}
