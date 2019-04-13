package com.banking.chestnut.depositRepositories;

import com.banking.chestnut.models.deposit.Capitalization;
import com.banking.chestnut.models.deposit.CapitalizationType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CapitalizationRepository extends CrudRepository<Capitalization, Long> {
    Optional<Capitalization> findByType(CapitalizationType type);
}
