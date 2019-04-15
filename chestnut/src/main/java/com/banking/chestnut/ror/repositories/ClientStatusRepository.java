package com.banking.chestnut.ror.repositories;

import com.banking.chestnut.models.ClientStatuses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientStatusRepository extends JpaRepository<ClientStatuses, Integer> {
}
