package com.banking.chestnut.credit.controllers;

import com.banking.chestnut.credit.services.CreditBalanceService;
import com.banking.chestnut.models.CreditBalance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/creditBalance")
public class CreditBalanceController {

    @Autowired
    CreditBalanceService creditBalanceService;

    UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

    @GetMapping(value = "/{id}")
    public ResponseEntity<CreditBalance> getCreditBalanceById(@PathVariable Integer id){
        try {
            CreditBalance creditBalance = creditBalanceService.getById(id);
            return ResponseEntity.ok().body(creditBalance);
        } catch(NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }

    //do poprawy
    @PostMapping("/add")
    public ResponseEntity<CreditBalance> addCredit(@RequestBody CreditBalance creditBalance) {
        try {
            CreditBalance createdCreditBalance = creditBalanceService.addCreditBalance(creditBalance);
            UriComponents uriComponents = uriBuilder.fromPath("/creditBalance/{id}").buildAndExpand(createdCreditBalance.getId());
            return ResponseEntity.created(uriComponents.toUri()).body(createdCreditBalance);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


}
