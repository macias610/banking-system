package com.banking.chestnut.moneytransfers.repositories;

import com.banking.chestnut.models.Transaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MoneyTransactionRepository extends CrudRepository<Transaction, Integer> {
    List<Transaction> findBySenderId_IdOrReceiverId_Id(int senderId, int receiverId);
    Transaction findById(int id);
}
