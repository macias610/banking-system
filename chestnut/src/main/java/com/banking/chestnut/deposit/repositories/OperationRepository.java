package com.banking.chestnut.deposit.repositories;

import com.banking.chestnut.models.DepositOperations;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface OperationRepository extends CrudRepository<DepositOperations, Integer> {
    Optional<Set<DepositOperations>> findAllByDepositId(Integer id);
}
