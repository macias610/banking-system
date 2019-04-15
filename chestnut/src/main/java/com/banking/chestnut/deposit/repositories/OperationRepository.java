package com.banking.chestnut.deposit.repositories;

import com.banking.chestnut.models.DepositOperations;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface OperationRepository extends CrudRepository<DepositOperations, Integer> {
    Optional<Set<DepositOperations>> findAllByDepositId(Integer id);
    
    @Query("SELECT o FROM DepositOperations o JOIN o.deposit d JOIN d.account a WHERE a.id = :accountId")
    Optional<Set<DepositOperations>> findAllByAccountId(@Param("accountId") Integer id);
    
}
