package com.banking.chestnut.deposit.services;

import com.banking.chestnut.deposit.repositories.DepositRepository;
import com.banking.chestnut.deposit.repositories.OperationRepository;
import com.banking.chestnut.models.AccountInfo;
import com.banking.chestnut.models.Deposit;
import com.banking.chestnut.models.Operation;
import com.banking.chestnut.deposit.helpers.OperationFactory;
import com.banking.chestnut.models.OperationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class DepositService {
    
    @Autowired
    DepositRepository depositRepository;
    
    @Autowired
    OperationRepository operationRepository;
    
    public Deposit getDepositById(Integer id) {
        return depositRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
    
    public Deposit closeDepositWithId(Integer id){
        Deposit depositToClose = depositRepository.findById(id).orElseThrow(NoSuchElementException::new);
        Operation closeOperation = OperationFactory.createOperation(OperationType.CLOSING,depositToClose);
        operationRepository.save(closeOperation);
        AccountInfo accountInfo = depositToClose.getAccount().getInfoId();
        Float depositAmount = depositToClose.getAmount();
        accountInfo.setAvailableAmount(depositAmount.longValue());
        depositToClose.setIsActive(false);
        return depositToClose;
    }
    
    public Set<Deposit> getDepositsByAccountId(Integer id){
        return depositRepository.findAllByAccountId(id).orElseThrow(NoSuchElementException::new);
    }
    
    public Deposit addDeposit(Deposit deposit) {
        Operation addOperation = OperationFactory.createOperation(OperationType.OPENING,deposit);
        operationRepository.save(addOperation);
        return depositRepository.save(deposit);
    }
    
    public void deleteDeposit(Integer id) {
        Deposit deposit = depositRepository.findById(id).orElseThrow(NoSuchElementException::new);
        depositRepository.delete(deposit);
    }
}
