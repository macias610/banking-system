package com.banking.chestnut.credit.repositories;

import com.banking.chestnut.models.CreditType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditTypeRepository extends CrudRepository<CreditType,Integer> {
}
