package com.banking.chestnut.credit.repositories;

import com.banking.chestnut.models.Credits;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface CreditRepository extends CrudRepository<Credits,Integer> {
    Optional<Set<Credits>> findAllByAccountId(Integer id);
}
