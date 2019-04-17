package com.banking.chestnut.credit.controllers;

import com.banking.chestnut.credit.services.CreditBalanceService;
import com.banking.chestnut.models.CreditBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/creditBalance")
public class CreditBalanceController {

    @Autowired
    CreditBalanceService creditBalanceService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<CreditBalance> getCreditBalanceById(@PathVariable Long id){
        try {
            CreditBalance creditBalance = creditBalanceService.getById(id);
            return ResponseEntity.ok().body(creditBalance);
        } catch(NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }


}
