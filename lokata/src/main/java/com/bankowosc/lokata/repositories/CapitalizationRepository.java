package com.bankowosc.lokata.repositories;

import com.bankowosc.lokata.models.Capitalization;
import com.bankowosc.lokata.models.CapitalizationType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CapitalizationRepository extends CrudRepository<Capitalization, Long> {
    Optional<Capitalization> findByType(CapitalizationType type);
}
