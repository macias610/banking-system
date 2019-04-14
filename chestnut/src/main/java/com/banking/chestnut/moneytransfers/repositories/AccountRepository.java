package com.banking.chestnut.moneytransfers.repositories;

import com.banking.chestnut.models.Accounts;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Accounts, Integer> {
    Accounts findByClientId(int clientId);
    Accounts findById(int id);
}
