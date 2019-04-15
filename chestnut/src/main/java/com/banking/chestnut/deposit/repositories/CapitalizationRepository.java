package com.banking.chestnut.deposit.repositories;

import com.banking.chestnut.models.CapitalizationType;
import com.banking.chestnut.models.DepositCapitalizations;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CapitalizationRepository extends CrudRepository<DepositCapitalizations, Integer> {
    Optional<DepositCapitalizations> findByType(CapitalizationType type);
}
