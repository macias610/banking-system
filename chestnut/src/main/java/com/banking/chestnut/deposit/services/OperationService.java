package com.banking.chestnut.deposit.services;

import com.banking.chestnut.deposit.repositories.OperationRepository;
import com.banking.chestnut.models.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class OperationService {
    
    @Autowired
    OperationRepository operationRepository;
    
    public Set<Operation> getOperationsByDepositId(Integer id) {
        return operationRepository.findAllByDepositId(id).orElseThrow(NoSuchElementException::new);
    }
    
    public Operation addOperation(Operation operation) {
        return operationRepository.save(operation);
    }
}
