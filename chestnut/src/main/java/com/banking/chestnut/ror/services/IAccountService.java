package com.banking.chestnut.ror.services;

import com.banking.chestnut.models.Account;
import com.banking.chestnut.models.Client;

import java.util.List;
import java.util.Optional;

public interface IAccountService {
    Account saveAccount(Account account, Client client);
    Optional<Account> getById(Integer id);
    List<Account> getAll();
}
