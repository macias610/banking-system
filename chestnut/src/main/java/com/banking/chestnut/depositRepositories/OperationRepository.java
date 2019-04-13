package com.banking.chestnut.depositRepositories;

import com.banking.chestnut.models.deposit.Operation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface OperationRepository extends CrudRepository<Operation, Long> {
    Optional<Set<Operation>> findAllByDepositId(Long id);
}
