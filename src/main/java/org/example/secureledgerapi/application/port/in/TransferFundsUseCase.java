package org.example.secureledgerapi.application.port.in;

import org.example.secureledgerapi.application.port.in.command.TransferFundsCommand;

// La interfaz es simple, define lo que puede hacer la aplicación.
public interface TransferFundsUseCase {

    // Retorna true si la transferencia fue exitosa
    boolean transferFunds(TransferFundsCommand command);
}