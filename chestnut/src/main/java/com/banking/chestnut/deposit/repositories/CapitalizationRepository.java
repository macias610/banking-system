package com.banking.chestnut.deposit.repositories;

import com.banking.chestnut.models.Capitalization;
import com.banking.chestnut.models.CapitalizationType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CapitalizationRepository extends CrudRepository<Capitalization, Integer> {
    Optional<Capitalization> findByType(CapitalizationType type);
}
