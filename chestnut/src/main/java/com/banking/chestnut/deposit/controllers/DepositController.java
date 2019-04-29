package com.banking.chestnut.deposit.controllers;

import com.banking.chestnut.deposit.dto.DepositDto;
import com.banking.chestnut.deposit.services.DepositService;
import com.banking.chestnut.models.ResponseObject;
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
    ResponseEntity getDepositById(@PathVariable Integer id) {
        try {
            DepositDto deposit = depositService.getDepositById(id);
            return ResponseEntity.ok().body(deposit);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping(value = "/account/{id}")
    ResponseEntity getDepositsByAccountId(@PathVariable Integer id) {
        try {
            Set<DepositDto> deposits = depositService.getDepositsByAccountId(id);
            return ResponseEntity.ok().body(deposits);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping()
    public ResponseEntity addDeposit(@RequestBody DepositDto depositDto) {
        try {
            DepositDto createdDeposit = depositService.addDeposit(depositDto);
            UriComponents uriComponents = uriBuilder.fromPath("/deposits/{id}").buildAndExpand(createdDeposit.getId());
            return ResponseEntity.created(uriComponents.toUri()).body(createdDeposit);
        } catch (NoSuchElementException e) {
            ResponseObject error = ResponseObject.createError(e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (UnsupportedOperationException e){
            ResponseObject error = ResponseObject.createError(e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity closeDepositById(@PathVariable Integer id) {
        try {
            depositService.closeDepositWithId(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(ResponseObject.createError(e.getMessage()));
        }
    }
}
