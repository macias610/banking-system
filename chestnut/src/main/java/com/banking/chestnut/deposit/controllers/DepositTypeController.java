package com.banking.chestnut.deposit.controllers;


import com.banking.chestnut.deposit.dto.DepositTypeDto;
import com.banking.chestnut.deposit.helpers.HateoasHelper;
import com.banking.chestnut.deposit.services.DepositTypeService;
import com.banking.chestnut.models.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.NoSuchElementException;
import java.util.Set;

import static com.banking.chestnut.deposit.helpers.ErrorMessages.CREATE_DEPOSIT_TYPE_ERROR;
import static com.banking.chestnut.deposit.helpers.JsonNodeCreator.createJsonNodeFrom;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/deposit-types")
public class
DepositTypeController {
    
    @Autowired
    DepositTypeService depositTypeService;
    
    @GetMapping("/{id}")
    ResponseEntity getDepositTypeById(@PathVariable Integer id) {
        try {
            DepositTypeDto depositTypeDto = depositTypeService.getDepositTypeById(id);
            ResponseObject success = ResponseObject.createSuccess("",createJsonNodeFrom(depositTypeDto));
            return ResponseEntity.ok().body(success);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }
    }
    
    @GetMapping()
    ResponseEntity getAllDepositTypes() {
        try {
            Set<DepositTypeDto> depositTypeDto = depositTypeService.getAllDepositTypes();
            ResponseObject success = ResponseObject.createSuccess("",createJsonNodeFrom(depositTypeDto));
            return ResponseEntity.ok().body(success);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }
    }
    
    @PostMapping()
    public ResponseEntity createDepositType(@RequestBody DepositTypeDto depositTypeDto) {
        
        try {
            DepositTypeDto createdDepositTypeDto = depositTypeService.addDepositType(depositTypeDto);
            URI uriForFetchingCreatedDepositType = HateoasHelper.getUriWithPathAndParams("/depositTypes/{id}",createdDepositTypeDto.getId());
            ResponseObject success = ResponseObject.createSuccess("",createJsonNodeFrom(createdDepositTypeDto));
            return ResponseEntity.created(uriForFetchingCreatedDepositType).body(success);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            String errorMessage = String.valueOf(CREATE_DEPOSIT_TYPE_ERROR);
            ResponseObject error = ResponseObject.createError(errorMessage);
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity deleteDepositTypeById(@PathVariable Integer id) {
        try {
            DepositTypeDto depositTypeDto = depositTypeService.deleteDepositTypeById(id);
            ResponseObject success = ResponseObject.createSuccess("",createJsonNodeFrom(depositTypeDto));
            return ResponseEntity.ok().body(success);
        } catch (NoSuchElementException e) {
            ResponseObject error = ResponseObject.createError(e.getMessage());
            return ResponseEntity.status(NOT_MODIFIED).body(error);
        }
    }
}
