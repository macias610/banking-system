package com.banking.chestnut.depositControllers;

import com.banking.chestnut.depositServices.OperationService;
import com.banking.chestnut.models.deposit.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping("/operations")
public class OperationsController {
    
    @Autowired
    OperationService operationService;
    
    @GetMapping("/deposit/{id}")
    public ResponseEntity<Set<Operation>> getOperationsByDepositId(Long id){
        try {
            Set<Operation> operations = operationService.getOperationsByDepositId(id);
            return ResponseEntity.ok().body(operations);
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }
}
