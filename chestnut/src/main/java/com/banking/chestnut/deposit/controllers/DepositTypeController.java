package com.banking.chestnut.deposit.controllers;


import com.banking.chestnut.deposit.dto.DepositTypeDto;
import com.banking.chestnut.deposit.services.DepositTypeService;
import com.banking.chestnut.models.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping("/deposit-types")
public class
DepositTypeController {
    
    @Autowired
    DepositTypeService depositTypeService;
    
    UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
    
    @GetMapping("/{id}")
    ResponseEntity<DepositTypeDto> getDepositTypeById(@PathVariable Integer id) {
        try {
            DepositTypeDto depositTypeDto = depositTypeService.getDepositTypeById(id);
            return ResponseEntity.ok().body(depositTypeDto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping()
    ResponseEntity<Set<DepositTypeDto>> getAllDepositTypes() {
        try {
            Set<DepositTypeDto> depositTypeDto = depositTypeService.getAllDepositTypes();
            return ResponseEntity.ok().body(depositTypeDto);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping()
    public ResponseEntity<DepositTypeDto> createDepositType(@RequestBody DepositTypeDto depositTypeDto) {
        
        try {
            DepositTypeDto createdDepositTypeDto = depositTypeService.addDepositType(depositTypeDto);
            UriComponents uriComponents = uriBuilder.fromPath("/depositTypes/{id}").buildAndExpand(createdDepositTypeDto.getId());
            return ResponseEntity.created(uriComponents.toUri()).body(createdDepositTypeDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity deleteDepositTypeById(@PathVariable Integer id) {
        try {
            depositTypeService.deleteDepositTypeById(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(ResponseObject.createError(e.getMessage()));
        }
    }
}
