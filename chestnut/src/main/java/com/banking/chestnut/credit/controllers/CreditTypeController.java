package com.banking.chestnut.credit.controllers;

import com.banking.chestnut.credit.services.CreditTypeService;
import com.banking.chestnut.models.CreditType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/creditType")
public class CreditTypeController {

    @Autowired
    CreditTypeService creditTypeService;

    UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

    @GetMapping(value = "/{id}")
    public ResponseEntity<CreditType> getCreditTypeById(@PathVariable Integer id){
        try {
            CreditType creditType = creditTypeService.getById(id);
            return ResponseEntity.ok().body(creditType);
        } catch(NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<CreditType> addCreditType(@RequestBody CreditType creditType) {
        try {
            CreditType createdCreditType = creditTypeService.addCreditType(creditType);
            UriComponents uriComponents = uriBuilder.fromPath("/paymentSchedule/{id}").buildAndExpand(createdCreditType.getId());
            return ResponseEntity.created(uriComponents.toUri()).body(createdCreditType);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
