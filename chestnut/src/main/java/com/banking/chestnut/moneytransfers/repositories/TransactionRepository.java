package com.banking.chestnut.moneytransfers.repositories;

import com.banking.chestnut.models.Transactions;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transactions, Integer> {
    List<Transactions> findAllBySenderIdOrReceiverId(int accountId);
    Transactions findById(int id);
}
