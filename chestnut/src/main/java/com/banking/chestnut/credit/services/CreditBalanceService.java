package com.banking.chestnut.credit.services;

import com.banking.chestnut.credit.repositories.CreditBalanceRepository;
import com.banking.chestnut.models.CreditBalance;
import com.banking.chestnut.models.DepositOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CreditBalanceService {

    @Autowired
    CreditBalanceRepository creditBalanceRepository;

    public CreditBalance getCreditBalanceByCreditId(Integer id) {
        return creditBalanceRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Can't find balance for credit with id: " + id));
    }

}
