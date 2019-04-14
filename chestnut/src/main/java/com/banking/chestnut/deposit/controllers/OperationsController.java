package com.banking.chestnut.deposit.controllers;

import com.banking.chestnut.deposit.services.OperationService;
import com.banking.chestnut.models.DepositOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping("/operation")
public class OperationsController {
    
    @Autowired
    OperationService operationService;
    
    @GetMapping("/deposit/{id}")
    public ResponseEntity<Set<DepositOperations>> getOperationsByDepositId(@PathVariable Integer id){
        try {
            Set<DepositOperations> depositOperations = operationService.getOperationsByDepositId(id);
            return ResponseEntity.ok().body(depositOperations);
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }
}
