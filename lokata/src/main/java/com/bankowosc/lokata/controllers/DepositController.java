package com.bankowosc.lokata.controllers;

import com.bankowosc.lokata.models.Deposit;
import com.bankowosc.lokata.services.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/deposit")
public class DepositController {
    
    @Autowired
    DepositService depositService;
    
    @GetMapping(value = "/{id}")
    HttpEntity<Deposit> getDepositById(@PathVariable Long id) {
        try {
            Deposit deposit = depositService.getDepositById(id);
            return ResponseEntity.ok().body(deposit);
        } catch (NoSuchElementException e){
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("")
    public ResponseEntity<Deposit> createPole(@RequestBody Deposit deposit) {
        try {
            Deposit createdDeposit = depositService.addDeposit(deposit);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDeposit);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity deleteDepositById(@PathVariable Long id) {
        try {
            depositService.deleteDeposit(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
