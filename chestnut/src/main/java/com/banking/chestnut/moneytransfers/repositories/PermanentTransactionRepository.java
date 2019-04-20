package com.banking.chestnut.moneytransfers.repositories;

import com.banking.chestnut.models.PermanentTransactions;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PermanentTransactionRepository extends CrudRepository<PermanentTransactions, Integer> {
    List<PermanentTransactions> findByReceiverId_IdOrSenderId_Id(int receiverId, int senderId);
    PermanentTransactions findById(int id);
}
