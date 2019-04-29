package com.banking.chestnut.deposit.controllers;

import com.banking.chestnut.deposit.dto.DepositDto;
import com.banking.chestnut.deposit.helpers.ErrorMessages;
import com.banking.chestnut.deposit.services.DepositService;
import com.banking.chestnut.models.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.NoSuchElementException;
import java.util.Set;

import static com.banking.chestnut.deposit.helpers.HateoasHelper.getUriWithPathAndParams;
import static com.banking.chestnut.deposit.helpers.JsonNodeCreator.createJsonNodeFrom;
import static com.banking.chestnut.models.ResponseObject.*;
import static com.banking.chestnut.models.ResponseObject.createSuccess;

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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @PostMapping()
    public ResponseEntity addDeposit(@RequestBody DepositDto depositDto) {
        try {
            DepositDto createdDeposit = depositService.addDeposit(depositDto);
//            UriComponents uriComponents = fromPath("/deposits/{id}").buildAndExpand(createdDeposit.getId());
            URI uriForFetchingCreatedDeposit = getUriWithPathAndParams("/deposits/{id}",createdDeposit.getId());
            ResponseObject success = createSuccess("", createJsonNodeFrom(createdDeposit));
            return ResponseEntity.created(uriForFetchingCreatedDeposit).body(success);
        } catch (NoSuchElementException | UnsupportedOperationException e) {
            ResponseObject error = createError(e.getMessage());
            return ResponseEntity.badRequest().body(error);
        } catch (Exception e){
            String errorMessage = String.valueOf(ErrorMessages.ADD_DEPOSIT_ERROR);
            ResponseObject error = createError(errorMessage);
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity closeDepositById(@PathVariable Integer id) {
        try {
            DepositDto depositDto = depositService.closeDepositWithId(id);
            ResponseObject success = createSuccess("", createJsonNodeFrom(depositDto));
            return ResponseEntity.status(HttpStatus.OK).body(success);
        } catch (NoSuchElementException e) {
            ResponseObject error = createError(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(error);
        }
    }
}
