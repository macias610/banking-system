package com.banking.chestnut.deposit.repositories;

import com.banking.chestnut.models.DepositTypes;
import com.banking.chestnut.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface DepositTypeRepository extends CrudRepository<DepositTypes, Integer> {
    Optional<List<DepositTypes>> findAllByDeletedAt(LocalDateTime localDateTime);

}
