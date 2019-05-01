package com.banking.chestnut.moneytransfers.repositories;

import com.banking.chestnut.models.Account;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransfersAccountRepository extends CrudRepository<Account, Integer> {
    Account findByClientId_Id(int clientId);
    Account findById(int id);
    Account findByNumberBankingAccount(String number);
    List<Account> findByType(String type);
}
