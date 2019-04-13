package com.bankowosc.lokata.repositories;

import com.bankowosc.lokata.models.Deposit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositRepository extends CrudRepository<Deposit, Long> {
}
