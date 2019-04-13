package com.bankowosc.lokata.services;

import com.bankowosc.lokata.models.Deposit;
import com.bankowosc.lokata.repositories.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class DepositService {
    
    @Autowired
    DepositRepository depositRepository;
    
    public Deposit getDepositById(Long id) {
        return depositRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
    
    public Deposit addDeposit(Deposit deposit) {
        return depositRepository.save(deposit);
    }
    
    public void deleteDeposit(Long id) {
        Deposit deposit = depositRepository.findById(id).orElseThrow(NoSuchElementException::new);
        depositRepository.delete(deposit);
    }
}
