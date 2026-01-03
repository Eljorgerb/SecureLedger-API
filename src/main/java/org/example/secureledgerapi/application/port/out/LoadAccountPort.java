package org.example.secureledgerapi.application.port.out;

import org.example.secureledgerapi.domain.model.Account;

import java.util.Optional;
import java.util.UUID;

public interface LoadAccountPort {

    Optional<Account> load(UUID accountId);
}
