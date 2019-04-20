package com.banking.chestnut.moneytransfers.repositories;

import com.banking.chestnut.models.Account;
import org.springframework.data.repository.CrudRepository;

public interface TransfersAccountRepository extends CrudRepository<Account, Integer> {
    Account findByClientId(int clientId);
    Account findById(int id);
}
