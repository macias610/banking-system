package com.banking.chestnut.moneytransfers.repositories;

import com.banking.chestnut.models.PermanentTransactions;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PermanentTransactionRepository extends CrudRepository<PermanentTransactions, Integer> {
    List<PermanentTransactions> findAllBySenderIdOrReceiverId(int accountId);
    PermanentTransactions findById(int id);
}
