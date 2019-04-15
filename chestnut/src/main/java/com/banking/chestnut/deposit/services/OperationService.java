package com.banking.chestnut.deposit.services;

import com.banking.chestnut.deposit.dto.DepositOperationDto;
import com.banking.chestnut.deposit.repositories.OperationRepository;
import com.banking.chestnut.models.DepositOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OperationService {
    
    @Autowired
    OperationRepository operationRepository;
    
    public Set<DepositOperationDto> getOperationsByDepositId(Integer id) {
        Set<DepositOperations> depositOperations = operationRepository.findAllByDepositId(id).orElseThrow(NoSuchElementException::new);
        return depositOperations.stream().map(o -> new DepositOperationDto(o)).collect(Collectors.toSet());
    }
    
    public Set<DepositOperationDto> getOperationsByAccountId(Integer id) {
        Set<DepositOperations> depositOperations = operationRepository.findAllByAccountId(id).orElseThrow(NoSuchElementException::new);
        return depositOperations.stream().map(o -> new DepositOperationDto(o)).collect(Collectors.toSet());
    }
}
