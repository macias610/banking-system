package com.banking.chestnut.moneytrasnfers.services;

import com.banking.chestnut.moneytrasnfers.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionService {

    private final TransactionRepository transactionRepository;
}
