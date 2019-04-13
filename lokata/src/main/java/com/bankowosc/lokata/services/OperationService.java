package com.bankowosc.lokata.services;

import com.bankowosc.lokata.models.Operation;
import com.bankowosc.lokata.repositories.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class OperationService {
    
    @Autowired
    OperationRepository operationRepository;
    
    public Operation getOperationById(Long id) {
        Optional<Operation> operation = operationRepository.findById(id);
        if (!operation.isPresent()) {
            throw new NoSuchElementException();
        }
        return operation.get();
    }
    
    public Operation addOperation(Operation operation) {
        return operationRepository.save(operation);
    }
}
