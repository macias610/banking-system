package com.banking.chestnut.credit.repositories;

import com.banking.chestnut.models.CreditType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CreditTypeRepository extends CrudRepository<CreditType,Integer> {
    Optional<List<CreditType>> findAllByDeletedAt(LocalDateTime localDateTime);
}
