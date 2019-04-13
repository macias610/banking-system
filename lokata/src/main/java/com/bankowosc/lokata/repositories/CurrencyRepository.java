package com.bankowosc.lokata.repositories;

import com.bankowosc.lokata.models.Currencies;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRepository extends CrudRepository<Currencies, Long> {
    
    Optional<Currencies> findByName(String name);
}
