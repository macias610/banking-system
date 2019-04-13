package com.bankowosc.lokata.controllers;

import com.bankowosc.lokata.models.DepositType;
import com.bankowosc.lokata.services.DepositTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/depositType")
public class DepositTypeController {
    
    @Autowired
    DepositTypeService depositTypeService;
    
    @GetMapping("/{id}")
    ResponseEntity<DepositType> getDepositTypeById(@PathVariable Long id) {
        try{
            DepositType depositType = depositTypeService.getDepositTypeById(id);
            return ResponseEntity.status(HttpStatus.OK).body(depositType);
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
    @PostMapping()
    public ResponseEntity<DepositType> createDepositType(@RequestBody DepositType depositType) {
        
        try{
            DepositType createdDepositType = depositTypeService.addDepositType(depositType);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDepositType);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity deleteDepositTypeById(@PathVariable Long id) {
        try{
            depositTypeService.deleteDepositTypeById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}