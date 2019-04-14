package com.banking.chestnut.deposit.services;

import com.banking.chestnut.commonrepositories.AccountsRepository;
import com.banking.chestnut.deposit.repositories.DepositRepository;
import com.banking.chestnut.deposit.repositories.OperationRepository;
import com.banking.chestnut.models.*;
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
    
    
    public Deposits getDepositById(Integer id) {
        return depositRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
    
    @Transactional
    public Deposits closeDepositWithId(Integer id) throws NoSuchElementException{
        Deposits depositsToClose = depositRepository.findById(id).orElseThrow(NoSuchElementException::new);
        DepositOperations closeDepositOperations = createOperation(OperationType.CLOSING, depositsToClose);
        operationRepository.save(closeDepositOperations);
        AccountInfo accountInfo = depositsToClose.getAccount().getInfoId();
        Float depositAmount = depositsToClose.getAmount();
        accountInfo.setAvailableAmount(depositAmount.longValue());
        depositsToClose.setEndDate(currentDate());
        depositsToClose.setIsActive(false);
        return depositsToClose;
    }
    
    public Set<Deposits> getDepositsByAccountId(Integer id){
        return depositRepository.findAllByAccountId(id).orElseThrow(NoSuchElementException::new);
    }
    
    @Transactional
    public Deposits addDeposit(Deposits deposits) {
        Deposits addedDeposits = depositRepository.save(deposits);
        DepositOperations addDepositOperations = createOperation(OperationType.OPENING, addedDeposits);
        operationRepository.save(addDepositOperations);
        return addedDeposits;
    }
    
    @Transactional
    public void deleteDeposit(Integer id) {
        Deposits deposits = depositRepository.findById(id).orElseThrow(NoSuchElementException::new);
        depositRepository.delete(deposits);
    }
}
