package org.example.secureledgerapi.infrastructure.adapter.in.web;

import org.example.secureledgerapi.domain.model.Account;
import org.example.secureledgerapi.domain.model.Money;
import org.example.secureledgerapi.domain.port.in.AccountServicePort;
import org.example.secureledgerapi.infrastructure.adapter.in.web.dto.TransferRequest;
import org.example.secureledgerapi.infrastructure.adapter.in.web.dto.AccountCreationRequest; // Importar el nuevo DTO
import org.springframework.http.HttpStatus; // Importar HttpStatus
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Currency;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountServicePort accountServicePort;

    // Inyectamos el Puerto (la interfaz del Dominio)
    public AccountController(AccountServicePort accountServicePort) {
        this.accountServicePort = accountServicePort;
    }

    /**
     * Endpoint para transferir fondos entre cuentas.
     * @param sourceAccountId El ID de la cuenta de origen (PathVariable).
     * @param request Datos de la transferencia (target, amount, currency).
     * @return ResponseEntity con la cuenta de origen actualizada.
     */
    @PostMapping("/{sourceAccountId}/transfer")
    public ResponseEntity<Account> transferFunds(
            @PathVariable UUID sourceAccountId,
            @Valid @RequestBody TransferRequest request) {

        // 1. ADAPTACIÓN: Convertimos el DTO (Infrastructure) al Value Object (Domain)
        Currency currency = Currency.getInstance(request.getCurrencyCode());
        Money money = new Money(request.getAmount(), currency);

        // 2. LLAMADA AL PUERTO: Llama al servicio de negocio
        Account updatedSourceAccount = accountServicePort.transferFunds(
                sourceAccountId,
                request.getTargetAccountId(),
                money
        );

        // 3. RESPUESTA: Devolvemos el estado de la cuenta.
        return ResponseEntity.ok(updatedSourceAccount);
    }

    /**
     * Endpoint para crear una nueva cuenta.
     * @param request Datos de la cuenta (ownerId, initialAmount, currencyCode).
     * @return ResponseEntity con la cuenta recién creada y estado 201 CREATED.
     */
    @PostMapping
    public ResponseEntity<Account> createAccount(@Valid @RequestBody AccountCreationRequest request) {

        // 1. ADAPTACIÓN: Convertimos el DTO (Infrastructure) al Value Object (Domain)
        Currency currency = Currency.getInstance(request.getCurrencyCode());
        Money initialBalance = new Money(request.getInitialAmount(), currency);

        // 2. LLAMADA AL PUERTO: Llama al servicio de negocio
        Account newAccount = accountServicePort.createAccount(
                request.getOwnerId(),
                initialBalance
        );

        // 3. RESPUESTA: Devolvemos el objeto Account con 201 CREATED
        return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
    }


    /**
     * Endpoint simple para obtener detalles de la cuenta
     */
    @GetMapping("/{accountId}")
    public ResponseEntity<Account> getAccountDetails(@PathVariable UUID accountId) {
        Account account = accountServicePort.getAccountDetails(accountId);

        // Manejo básico si la cuenta no se encuentra (asumiendo que getAccountDetails lanza una excepción)
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(account);
    }
}