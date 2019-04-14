package com.banking.chestnut.deposit.services;

import com.banking.chestnut.deposit.repositories.OperationRepository;
import com.banking.chestnut.models.DepositOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class OperationService {
    
    @Autowired
    OperationRepository operationRepository;
    
    public Set<DepositOperations> getOperationsByDepositId(Integer id) {
        return operationRepository.findAllByDepositId(id).orElseThrow(NoSuchElementException::new);
    }
    
    public DepositOperations addOperation(DepositOperations depositOperations) {
        return operationRepository.save(depositOperations);
    }
}
