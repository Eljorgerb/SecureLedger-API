package org.example.secureledgerapi.infrastructure.adapter.out.persistence;

import org.example.secureledgerapi.infrastructure.adapter.out.persistence.entity.AccountJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

// JpaRepository proporciona métodos como findById y save
public interface AccountRepository extends JpaRepository<AccountJpaEntity, UUID> {

    // Spring Data JPA ya proporciona findById y save/saveAndFlush
}