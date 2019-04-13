package com.banking.chestnut.depositRepositories;

import com.banking.chestnut.models.deposit.Deposit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface DepositRepository extends CrudRepository<Deposit, Integer> {
    Optional<Set<Deposit>> findAllByAccountId(Integer id);
}
