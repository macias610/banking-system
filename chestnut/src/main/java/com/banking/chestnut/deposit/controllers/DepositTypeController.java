package com.banking.chestnut.deposit.controllers;


import com.banking.chestnut.deposit.services.DepositTypeService;
import com.banking.chestnut.models.DepositType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/depositType")
public class DepositTypeController {
    
    @Autowired
    DepositTypeService depositTypeService;
    
    UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
    
    @GetMapping("/{id}")
    ResponseEntity<DepositType> getDepositTypeById(@PathVariable Integer id) {
        try{
            DepositType depositType = depositTypeService.getDepositTypeById(id);
            return ResponseEntity.ok().body(depositType);
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping()
    public ResponseEntity<DepositType> createDepositType(@RequestBody DepositType depositType) {
        
        try{
            DepositType createdDepositType = depositTypeService.addDepositType(depositType);
            UriComponents uriComponents = uriBuilder.fromPath("/depositType/{id}").buildAndExpand(createdDepositType.getId());
            return ResponseEntity.created(uriComponents.toUri()).body(createdDepositType);
        } catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
        
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity deleteDepositTypeById(@PathVariable Integer id) {
        try{
            depositTypeService.deleteDepositTypeById(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e){
            return ResponseEntity.badRequest().build();
        }
    }
}
