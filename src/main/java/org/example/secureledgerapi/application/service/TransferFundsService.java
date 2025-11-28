package org.example.secureledgerapi.application.service;

import org.example.secureledgerapi.application.port.in.TransferFundsUseCase;
import org.example.secureledgerapi.application.port.in.command.TransferFundsCommand;
import org.example.secureledgerapi.application.port.out.LoadAccountPort;
import org.example.secureledgerapi.application.port.out.UpdateAccountStatePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional // CRUCIAL: Asegura que el débito y el crédito sean atómicos.
public class TransferFundsService implements TransferFundsUseCase {

    private final LoadAccountPort loadAccountPort;
    private final UpdateAccountStatePort updateAccountStatePort;

    // Inyección de dependencias (necesitas los ports Outbound)
    public TransferFundsService(LoadAccountPort loadAccountPort, UpdateAccountStatePort updateAccountStatePort) {
        this.loadAccountPort = loadAccountPort;
        this.updateAccountStatePort = updateAccountStatePort;
    }

    @Override
    public boolean transferFunds(TransferFundsCommand command) {

        // 1. Cargar las cuentas (la carga debe ser forzada, idealmente con bloqueo pesimista en un entorno real)
        // Para simplificar, asumimos que AccountJpaEntity es el modelo de dominio aquí.
        // En una arquitectura limpia, cargarías una entidad de Dominio 'Account'.
        var sourceAccount = loadAccountPort.loadAccount(command.getSourceAccountId());
        var targetAccount = loadAccountPort.loadAccount(command.getTargetAccountId());

        // 2. Validaciones (ej: saldo suficiente, monedas compatibles)
        if (sourceAccount == null || targetAccount == null) {
            throw new IllegalArgumentException("One or both accounts not found.");
        }

        if (sourceAccount.getBalanceAmount().compareTo(command.getAmount()) < 0) {
            throw new IllegalStateException("Insufficient balance in source account.");
        }

        // 3. Modificación del estado (lógica de negocio)

        // Débito de la cuenta origen
        sourceAccount.setBalanceAmount(
                sourceAccount.getBalanceAmount().subtract(command.getAmount())
        );

        // Crédito a la cuenta destino
        targetAccount.setBalanceAmount(
                targetAccount.getBalanceAmount().add(command.getAmount())
        );

        // 4. Persistir los cambios (si la transacción falla aquí, Spring/JPA hace rollback automático)
        updateAccountStatePort.updateAccount(sourceAccount);
        updateAccountStatePort.updateAccount(targetAccount);

        // Si la persistencia es exitosa, el campo @Version de ambas cuentas se incrementará.
        // Si el chequeo Optimistic Locking falla en *cualquiera* de las dos cuentas,
        // toda la transacción falla y hace Rollback.

        return true;
    }
}