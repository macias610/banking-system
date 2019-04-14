package com.banking.chestnut.deposit.controllers;


import com.banking.chestnut.deposit.services.DepositTypeService;
import com.banking.chestnut.models.DepositTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/depositTypes")
public class DepositTypeController {
    
    @Autowired
    DepositTypeService depositTypeService;
    
    UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
    
    @GetMapping("/{id}")
    ResponseEntity<DepositTypes> getDepositTypeById(@PathVariable Integer id) {
        try{
            DepositTypes depositTypes = depositTypeService.getDepositTypeById(id);
            return ResponseEntity.ok().body(depositTypes);
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping()
    public ResponseEntity<DepositTypes> createDepositType(@RequestBody DepositTypes depositTypes) {
        
        try{
            DepositTypes createdDepositTypes = depositTypeService.addDepositType(depositTypes);
            UriComponents uriComponents = uriBuilder.fromPath("/depositTypes/{id}").buildAndExpand(createdDepositTypes.getId());
            return ResponseEntity.created(uriComponents.toUri()).body(createdDepositTypes);
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
