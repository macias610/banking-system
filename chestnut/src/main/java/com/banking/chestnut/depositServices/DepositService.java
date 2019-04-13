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
    
    
    public Deposit getDepositById(Integer id) {
        return depositRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
    
    public Deposit stopDepositWithId(Integer id){
        return null;
//        Deposit depositToStop = depositRepository.findById(id).orElseThrow(NoSuchElementException::new);
//        Accounts account = depositToStop.getAccount();
//        AccountInfo accountInfo = account.getInfoId();
//        Float depositAmount = depositToStop.getDepositType().getAmount();
//        accountInfo.setAvailableAmount();
    }
    
    public Set<Deposit> getDepositsByAccountId(Integer id){
        return depositRepository.findAllByAccountId(id).orElseThrow(NoSuchElementException::new);
    }
    
    public Deposit addDeposit(Deposit deposit) {
        return depositRepository.save(deposit);
    }
    
    public void deleteDeposit(Integer id) {
        Deposit deposit = depositRepository.findById(id).orElseThrow(NoSuchElementException::new);
        depositRepository.delete(deposit);
    }
}
