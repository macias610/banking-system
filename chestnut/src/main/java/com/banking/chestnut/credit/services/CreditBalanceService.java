package com.banking.chestnut.credit.services;

import com.banking.chestnut.credit.repositories.CreditBalanceRepository;
import com.banking.chestnut.models.CreditBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CreditBalanceService {

    @Autowired
    CreditBalanceRepository creditBalanceRepository;

    public CreditBalance getById(Long id){
        return creditBalanceRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public CreditBalance addCreditBalance(CreditBalance creditBalance){
        return creditBalanceRepository.save(creditBalance);
    }

}
