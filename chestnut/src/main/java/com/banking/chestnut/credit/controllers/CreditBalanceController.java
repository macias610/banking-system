package com.banking.chestnut.credit.controllers;

import com.banking.chestnut.credit.services.CreditBalanceService;
import com.banking.chestnut.models.CreditBalance;
import com.banking.chestnut.models.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    CreditBalanceService creditBalanceService;

    @GetMapping("/{id}")
    public ResponseEntity getCreditBalanceById(@PathVariable Integer id) {
    try {
        CreditBalance creditBalance = creditBalanceService.getCreditBalanceByCreditId(id);
        ResponseObject success = createSuccess("", createJsonNodeFrom(creditBalance));
        return ResponseEntity.ok().body(success);
    } catch (NoSuchElementException e) {
        ResponseObject error = createError(e.getMessage());
        return ResponseEntity.status(NOT_FOUND).body(error);
    }
    }


}
