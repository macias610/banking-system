package com.banking.chestnut.deposit.controllers;

import com.banking.chestnut.deposit.dto.DepositOperationDto;
import com.banking.chestnut.deposit.services.OperationService;
import com.banking.chestnut.models.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;
import java.util.Set;

import static com.banking.chestnut.deposit.helpers.JsonNodeCreator.createJsonNodeFrom;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/operation")
public class OperationsController {
    
    @Autowired
    OperationService operationService;
    
    @GetMapping("/deposit/{id}")
    public ResponseEntity getOperationsByDepositId(@PathVariable Integer id) {
        try {
            Set<DepositOperationDto> depositOperations = operationService.getOperationsByDepositId(id);
            ResponseObject success = ResponseObject.createSuccess("", createJsonNodeFrom(depositOperations));
            return ResponseEntity.ok().body(success);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }
    }
    
    @GetMapping("/account/{id}")
    public ResponseEntity getOperationsByAccountId(@PathVariable Integer id) {
        try {
            Set<DepositOperationDto> depositOperations = operationService.getOperationsByAccountId(id);
            ResponseObject success = ResponseObject.createSuccess("", createJsonNodeFrom(depositOperations));
            return ResponseEntity.ok().body(success);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }
    }
}
