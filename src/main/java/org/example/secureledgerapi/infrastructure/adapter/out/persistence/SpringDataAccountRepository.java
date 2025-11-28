package org.example.secureledgerapi.infrastructure.adapter.out.persistence;

import org.example.secureledgerapi.infrastructure.adapter.out.persistence.entity.AccountJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

// Spring Data JPA nos da la implementación automática de findById, save, etc.
public interface SpringDataAccountRepository extends JpaRepository<AccountJpaEntity, UUID> {

}
