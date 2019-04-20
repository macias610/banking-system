package com.banking.chestnut.moneytransfers.repositories;

import com.banking.chestnut.models.Transactions;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transactions, Integer> {
    List<Transactions> findBySenderId_IdOrReceiverId_Id(int senderId, int receiverId);
    Transactions findById(int id);
}
