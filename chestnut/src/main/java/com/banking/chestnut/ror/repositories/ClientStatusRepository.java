package com.banking.chestnut.ror.repositories;

import com.banking.chestnut.models.ClientStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientStatusRepository extends JpaRepository<ClientStatus, Integer> {
}
