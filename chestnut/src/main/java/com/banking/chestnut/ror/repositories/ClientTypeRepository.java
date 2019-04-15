package com.banking.chestnut.ror.repositories;

import com.banking.chestnut.models.ClientTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientTypeRepository extends JpaRepository<ClientTypes, Integer> {
}
