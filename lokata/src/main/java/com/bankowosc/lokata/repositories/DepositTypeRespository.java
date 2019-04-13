package com.bankowosc.lokata.repositories;

import com.bankowosc.lokata.models.DepositType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositTypeRespository extends CrudRepository<DepositType, Long> {

}
