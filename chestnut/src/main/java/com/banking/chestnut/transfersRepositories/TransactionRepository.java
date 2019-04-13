package com.banking.chestnut.transfersRepositories;

import com.banking.chestnut.models.Transactions;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<Transactions, Integer> {

}
