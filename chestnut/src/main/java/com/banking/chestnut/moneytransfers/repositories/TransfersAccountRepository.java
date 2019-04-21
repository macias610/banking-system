package com.banking.chestnut.moneytransfers.repositories;

import com.banking.chestnut.models.Account;
import org.springframework.data.repository.CrudRepository;

public interface TransfersAccountRepository extends CrudRepository<Account, Integer> {
    Account findByClientId_Id(int clientId);
    Account findById(int id);
    Account findByNumberClientAccount(String number);
}
