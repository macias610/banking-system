package com.banking.chestnut.credit.repositories;

import com.banking.chestnut.models.CreditBalance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CreditBalanceRepository extends CrudRepository<CreditBalance, Integer> {
}
