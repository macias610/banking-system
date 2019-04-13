package com.banking.chestnut.deposit.repositories;

import com.banking.chestnut.models.DepositType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositTypeRepository extends CrudRepository<DepositType, Integer> {

}
