package com.banking.chestnut.ror.services;

import com.banking.chestnut.models.Account;
import com.banking.chestnut.models.Client;
import com.banking.chestnut.models.Transaction;
import com.banking.chestnut.ror.dto.TransactionDto;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
    Account saveAccount(Account account, Client client);
    Account editAccount(Account account);
    Account deleteAccount(Account account);
    List<Transaction> getTransactionsByAccount(TransactionDto transactionDto, Integer accountId);
    Optional<Account> getById(Integer id);
    List<Account> getAll();
    List<Account> getClientAccounts(Integer clientId);
}
