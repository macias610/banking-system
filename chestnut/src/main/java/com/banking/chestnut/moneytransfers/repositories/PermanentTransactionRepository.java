package com.banking.chestnut.moneytransfers.repositories;

import com.banking.chestnut.models.PermanentTransactions;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface PermanentTransactionRepository extends CrudRepository<PermanentTransactions, Integer> {
    List<PermanentTransactions> findBySenderId_Id(int senderId);
    PermanentTransactions findById(int id);
    List<PermanentTransactions> findByNextDateAndIsEnabled(Date nextDate, boolean isEnabled);
}
