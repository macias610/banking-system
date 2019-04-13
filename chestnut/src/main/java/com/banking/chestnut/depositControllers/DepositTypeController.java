package com.banking.chestnut.depositControllers;


import com.banking.chestnut.depositServices.DepositTypeService;
import com.banking.chestnut.models.deposit.DepositType;
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
            return ResponseEntity.status(HttpStatus.CREATED).body(createdDepositType);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity deleteDepositTypeById(@PathVariable Integer id) {
        try{
            depositTypeService.deleteDepositTypeById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
