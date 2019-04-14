package com.banking.chestnut.deposit.controllers;

import com.banking.chestnut.deposit.services.DepositService;
import com.banking.chestnut.models.Deposits;
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
    ResponseEntity<Deposits> getDepositById(@PathVariable Integer id) {
        try {
            Deposits deposits = depositService.getDepositById(id);
            return ResponseEntity.ok().body(deposits);
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping(value = "/account/{id}")
    ResponseEntity<Set<Deposits>> getDepositsByAccountId(@PathVariable Integer id) {
        try {
            Set<Deposits> deposits = depositService.getDepositsByAccountId(id);
            return ResponseEntity.ok().body(deposits);
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping("/add")
    public ResponseEntity<Deposits> addDeposit(@RequestBody Deposits deposits) {
        try {
            Deposits createdDeposits = depositService.addDeposit(deposits);
            UriComponents uriComponents = uriBuilder.fromPath("/deposits/{id}").buildAndExpand(createdDeposits.getId());
            return ResponseEntity.created(uriComponents.toUri()).body(createdDeposits);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/close/{id}")
    public ResponseEntity<Deposits> closeDepositWithId(@PathVariable Integer id){
        try {
            Deposits deposits = depositService.closeDepositWithId(id);
            return ResponseEntity.ok().body(deposits);
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
