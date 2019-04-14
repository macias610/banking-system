package com.banking.chestnut.deposit.controllers;

import com.banking.chestnut.deposit.services.DepositService;
import com.banking.chestnut.models.Deposit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping("/deposit")
public class DepositController {
    
    @Autowired
    DepositService depositService;
    
    UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
    
    @GetMapping(value = "/{id}")
    ResponseEntity<Deposit> getDepositById(@PathVariable Integer id) {
        try {
            Deposit deposit = depositService.getDepositById(id);
            return ResponseEntity.ok().body(deposit);
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping(value = "/account/{id}")
    ResponseEntity<Set<Deposit>> getDepositsByAccountId(@PathVariable Integer id) {
        try {
            Set<Deposit> deposits = depositService.getDepositsByAccountId(id);
            return ResponseEntity.ok().body(deposits);
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/add")
    public ResponseEntity<Deposit> addDeposit(@RequestBody Deposit deposit) {
        try {
            Deposit createdDeposit = depositService.addDeposit(deposit);
            UriComponents uriComponents = uriBuilder.fromPath("/deposit/{id}").buildAndExpand(createdDeposit.getId());
            return ResponseEntity.created(uriComponents.toUri()).body(createdDeposit);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/close/{id}")
    public ResponseEntity<Deposit> closeDepositWithId(@PathVariable Integer id){
        try {
            Deposit deposit = depositService.closeDepositWithId(id);
            return ResponseEntity.ok().body(deposit);
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity deleteDepositById(@PathVariable Integer id) {
        try {
            depositService.deleteDeposit(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
