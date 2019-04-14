package com.banking.chestnut.deposit.services;

import com.banking.chestnut.commonrepositories.AccountsRepository;
import com.banking.chestnut.deposit.helpers.DateHelper;
import com.banking.chestnut.deposit.repositories.DepositRepository;
import com.banking.chestnut.deposit.repositories.OperationRepository;
import com.banking.chestnut.models.*;
import com.banking.chestnut.deposit.helpers.OperationFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Set;

import static com.banking.chestnut.deposit.helpers.DateHelper.*;
import static com.banking.chestnut.deposit.helpers.OperationFactory.createOperation;

@Service
public class DepositService {
    
    @Autowired
    DepositRepository depositRepository;
    
    @Autowired
    OperationRepository operationRepository;
    
    @Autowired
    AccountsRepository accountsRepository;
    
    public Deposit getDepositById(Integer id) {
        return depositRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
    
    @Transactional
    public Deposit closeDepositWithId(Integer id) throws NoSuchElementException{
        Deposit depositToClose = depositRepository.findById(id).orElseThrow(NoSuchElementException::new);
        Operation closeOperation = createOperation(OperationType.CLOSING,depositToClose);
        operationRepository.save(closeOperation);
        AccountInfo accountInfo = depositToClose.getAccount().getInfoId();
        Float depositAmount = depositToClose.getAmount();
        accountInfo.setAvailableAmount(depositAmount.longValue());
        depositToClose.setEndDate(currentDate());
        depositToClose.setIsActive(false);
        return depositToClose;
    }
    
    public Set<Deposit> getDepositsByAccountId(Integer id){
        return depositRepository.findAllByAccountId(id).orElseThrow(NoSuchElementException::new);
    }
    
    @Transactional
    public Deposit addDeposit(Deposit deposit) {
        Deposit addedDeposit = depositRepository.save(deposit);
        Operation addOperation = createOperation(OperationType.OPENING,addedDeposit);
        operationRepository.save(addOperation);
        return addedDeposit;
    }
    
    @Transactional
    public void deleteDeposit(Integer id) {
        Deposit deposit = depositRepository.findById(id).orElseThrow(NoSuchElementException::new);
        depositRepository.delete(deposit);
    }
}
