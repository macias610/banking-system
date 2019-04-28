package com.banking.chestnut.ror.services;

import com.banking.chestnut.models.Account;
import com.banking.chestnut.models.Client;

import java.util.List;

public interface IAccountService {
    Account saveAccount(Account account, Client client);
    List<Account> getAll();
}
