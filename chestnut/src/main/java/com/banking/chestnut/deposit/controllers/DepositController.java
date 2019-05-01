package com.banking.chestnut.deposit.controllers;

import com.banking.chestnut.deposit.dto.DepositDto;
import com.banking.chestnut.deposit.helpers.Messages;
import com.banking.chestnut.deposit.services.DepositService;
import com.banking.chestnut.models.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.NoSuchElementException;
import java.util.Set;

import static com.banking.chestnut.deposit.helpers.Messages.*;
import static com.banking.chestnut.deposit.helpers.HateoasHelper.getUriWithPathAndParams;
import static com.banking.chestnut.deposit.helpers.JsonNodeCreator.createJsonNodeFrom;
import static com.banking.chestnut.models.ResponseObject.*;
import static com.banking.chestnut.models.ResponseObject.createSuccess;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_MODIFIED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/deposit")
public class DepositController {
    
    @Autowired
    DepositService depositService;
    
    @GetMapping(value = "/{id}")
    ResponseEntity getDepositById(@PathVariable Integer id) {
        try {
            DepositDto deposit = depositService.getDepositById(id);
            ResponseObject responseBody = createSuccess("", createJsonNodeFrom(deposit));
            return ResponseEntity.ok().body(responseBody);
        } catch (NoSuchElementException e) {
            ResponseObject error = createError(e.getMessage());
            return ResponseEntity.status(NOT_FOUND).body(error);
        }
    }
    
    @GetMapping(value = "/account/{id}")
    ResponseEntity getDepositsByAccountId(@PathVariable Integer id) {
        try {
            Set<DepositDto> deposits = depositService.getDepositsByAccountId(id);
            ResponseObject success = createSuccess("", createJsonNodeFrom(deposits));
            return ResponseEntity.ok().body(success);
        } catch (NoSuchElementException e) {
            ResponseObject error = createError(e.getMessage());
            return ResponseEntity.status(NOT_FOUND).body(error);
        }
    }
    
    @PostMapping()
    public ResponseEntity addDeposit(@RequestBody DepositDto depositDto) {
        try {
            DepositDto createdDeposit = depositService.addDeposit(depositDto);
            URI uriForFetchingCreatedDeposit = getUriWithPathAndParams("/deposits/{id}",createdDeposit.getId());
            String successMessage = String.valueOf(ADD_DEPOSIT_SUCCESS);
            ResponseObject success = createSuccess(successMessage, createJsonNodeFrom(createdDeposit));
            return ResponseEntity.created(uriForFetchingCreatedDeposit).body(success);
        } catch (NoSuchElementException | UnsupportedOperationException e) {
            ResponseObject error = createError(e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e){
            String errorMessage = String.valueOf(ADD_DEPOSIT_ERROR);
            ResponseObject error = createError(errorMessage);
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity closeDepositById(@PathVariable Integer id) {
        try {
            DepositDto depositDto = depositService.closeDepositWithId(id);
            String successMessage = String.valueOf(CLOSE_DEPOSIT_SUCCESS);
            ResponseObject success = createSuccess(successMessage, createJsonNodeFrom(depositDto));
            return ResponseEntity.status(OK).body(success);
        } catch (NoSuchElementException e) {
            ResponseObject error = createError(e.getMessage());
            return ResponseEntity.status(NOT_MODIFIED).body(error);
        }
    }
}
