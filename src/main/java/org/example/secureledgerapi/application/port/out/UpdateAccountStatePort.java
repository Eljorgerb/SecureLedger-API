package org.example.secureledgerapi.application.port.out;

import org.example.secureledgerapi.domain.model.Account;

public interface UpdateAccountStatePort {

    Account update(Account account);
}
