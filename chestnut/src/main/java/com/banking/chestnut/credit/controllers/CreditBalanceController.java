package com.banking.chestnut.credit.controllers;

import com.banking.chestnut.credit.services.CreditBalanceService;
import com.banking.chestnut.models.CreditBalance;
import com.banking.chestnut.models.ResponseObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.NoSuchElementException;

import static com.banking.chestnut.credit.helpers.JsonNodeCreator.createJsonNodeFrom;
import static com.banking.chestnut.models.ResponseObject.createError;
import static com.banking.chestnut.models.ResponseObject.createSuccess;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/creditBalance")
public class CreditBalanceController {

    private static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    CreditBalanceService creditBalanceService;

    @GetMapping("/{id}")
    public ResponseEntity getCreditBalanceById(@PathVariable Integer id) {
        try {
            CreditBalance creditBalance = creditBalanceService.getCreditBalanceById(id);
            JsonNode returnData = mapper.valueToTree(creditBalance);
            return new ResponseEntity<>(ResponseObject.createSuccess("", returnData), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(ResponseObject.createError("CREDIT BALANCE NOT FOUND"), HttpStatus.NOT_FOUND);
        }
    }


}
