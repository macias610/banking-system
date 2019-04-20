package com.banking.chestnut.credit.repositories;

import com.banking.chestnut.models.CreditBalance;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditBalanceRepository extends CrudRepository<CreditBalance, Integer> {
}
