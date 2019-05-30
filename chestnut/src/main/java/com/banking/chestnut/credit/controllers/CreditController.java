package com.banking.chestnut.credit.controllers;

import com.banking.chestnut.credit.dto.CreditDto;
import com.banking.chestnut.credit.services.CreditService;
import com.banking.chestnut.models.ResponseObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Set;

import static com.banking.chestnut.credit.helpers.JsonNodeCreator.createJsonNodeFrom;
import static com.banking.chestnut.credit.helpers.Messages.*;
import static com.banking.chestnut.models.ResponseObject.createError;
import static com.banking.chestnut.models.ResponseObject.createSuccess;
import static java.lang.String.valueOf;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_MODIFIED;

@RestController
@RequestMapping("/credit")
public class CreditController {

    private static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    CreditService creditService;

    @GetMapping(value = "/{id}")
    ResponseEntity getCreditById(@PathVariable Integer id) {
        try {
            CreditDto credit = creditService.getCreditDtoById(id);
            JsonNode returnData = mapper.valueToTree(credit);
            return new ResponseEntity<>(ResponseObject.createSuccess("", returnData), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(ResponseObject.createError("CREDIT NOT FOUND"), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "account/{id}")
    ResponseEntity getCreditByAccountId(@PathVariable Integer id){
        try {
            Set<CreditDto> credits = creditService.getCreditsByAccountId(id);
            ResponseObject success = createSuccess("", createJsonNodeFrom(credits));
            return ResponseEntity.ok().body(success);
        } catch (NoSuchElementException e){
            ResponseObject error = createError(e.getMessage());
            return ResponseEntity.status(NOT_FOUND).body(error);
        }
    }

    @PostMapping()
    public ResponseEntity addCredit(@RequestBody CreditDto creditDto) {
        try {
            creditService.addCredit(creditDto);
            String successMessage = String.valueOf(ADD_CREDIT_SUCCESS);
            return  new ResponseEntity<>(ResponseObject.createSuccess(successMessage), HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
            String errorMessage = String.valueOf(ADD_CREDIT_ERROR);
            return new ResponseEntity<>(ResponseObject.createError(errorMessage), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity closeCreditById(@PathVariable Integer id){
        try{
            CreditDto creditDto = creditService.closeCreditWithId(id);
            String successMessage = valueOf(CLOSE_CREDIT_SUCCESS);
            ResponseObject success = createSuccess(successMessage, createJsonNodeFrom(creditDto));
            return ResponseEntity.ok().body(success);
        } catch (NoSuchElementException e) {
            ResponseObject error = createError(e.getMessage());
            return ResponseEntity.status(NOT_MODIFIED).body(error);
        }
    }

    @PatchMapping("/remit/{id}")
    public ResponseEntity remitCreditById(@PathVariable Integer id){
        try{
            CreditDto creditDto = creditService.remitCreditWithId(id);
            String successMessage = valueOf(REMIT_CREDIT_SUCCESS);
            ResponseObject success = createSuccess(successMessage, createJsonNodeFrom(creditDto));
            return ResponseEntity.ok().body(success);
        } catch (NoSuchElementException e) {
            ResponseObject error = createError(e.getMessage());
            return ResponseEntity.status(NOT_MODIFIED).body(error);
        }
    }

}
