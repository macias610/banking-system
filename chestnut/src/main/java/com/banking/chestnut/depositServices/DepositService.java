package com.banking.chestnut.depositServices;

import com.banking.chestnut.depositRepositories.DepositRepository;
import com.banking.chestnut.models.deposit.Deposit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class DepositService {
    
    @Autowired
    DepositRepository depositRepository;
    
    public Deposit getDepositById(Long id) {
        return depositRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
    
    public Set<Deposit> getDepositsByAccountId(Long id){
        return depositRepository.findAllByAccountId(id).orElseThrow(NoSuchElementException::new);
    }
    
    public Deposit addDeposit(Deposit deposit) {
        return depositRepository.save(deposit);
    }
    
    public void deleteDeposit(Long id) {
        Deposit deposit = depositRepository.findById(id).orElseThrow(NoSuchElementException::new);
        depositRepository.delete(deposit);
    }
}
