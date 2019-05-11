package com.banking.chestnut.ror.services;

import com.banking.chestnut.commonrepositories.UserRepository;
import com.banking.chestnut.models.Transaction;
import com.banking.chestnut.ror.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class TransactionService implements ITransactionService {

    private TransactionRepository transactionRepository;

    private Environment env;

    private Integer cashierId;

    private UserRepository userRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, Environment env, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.env = env;
        this.userRepository = userRepository;
        this.cashierId = Integer.parseInt(env.getProperty("app.cashier.user.id"));
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        transaction.setCreatedAt(new Date());
        transaction.setTransactionDate(new Date());
        transaction.setCreatedBy(userRepository.findById(cashierId).get());
        this.transactionRepository.save(transaction);
        return transaction;
    }
}
