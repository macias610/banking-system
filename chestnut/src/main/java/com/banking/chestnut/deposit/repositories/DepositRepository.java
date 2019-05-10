package com.banking.chestnut.deposit.repositories;

import com.banking.chestnut.models.Deposits;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Set;

@Repository
public interface DepositRepository extends CrudRepository<Deposits, Integer> {
    Optional<Set<Deposits>> findAllByAccountId(Integer id);
    Optional<Set<Deposits>> findAllByIsActive(Boolean isActive);
 }