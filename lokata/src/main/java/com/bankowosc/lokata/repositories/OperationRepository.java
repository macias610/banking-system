package com.bankowosc.lokata.repositories;

import com.bankowosc.lokata.models.Operation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends CrudRepository<Operation, Long> {
}
