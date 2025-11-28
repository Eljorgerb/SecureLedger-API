package org.example.secureledgerapi;

import org.springframework.boot.SpringApplication;

public class TestSecureLedgerApiApplication {

    public static void main(String[] args) {
        SpringApplication.from(SecureLedgerApiApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
