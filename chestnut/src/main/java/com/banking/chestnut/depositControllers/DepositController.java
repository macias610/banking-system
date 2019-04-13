package com.banking.chestnut.depositControllers;

import com.banking.chestnut.depositServices.DepositService;
import com.banking.chestnut.models.deposit.Deposit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping("/deposit")
public class DepositController {
    
    @Autowired
    DepositService depositService;
    
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
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDeposit);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/stop/{id}")
    public ResponseEntity<Deposit> stopDepositWithId(@PathVariable Integer id){
        try {
            Deposit deposit = depositService.stopDepositWithId(id);
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
