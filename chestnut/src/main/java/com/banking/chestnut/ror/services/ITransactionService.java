package com.banking.chestnut.ror.services;

import com.banking.chestnut.models.Transaction;

public interface ITransactionService {
    Transaction saveTransaction(Transaction transaction);
}
