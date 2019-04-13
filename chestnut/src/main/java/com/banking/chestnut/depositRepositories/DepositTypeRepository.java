package com.banking.chestnut.depositRepositories;

import com.banking.chestnut.models.deposit.DepositType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositTypeRepository extends CrudRepository<DepositType, Long> {

}
