package com.banking.chestnut.credit.controllers;

import com.banking.chestnut.credit.services.CreditTypeService;
import com.banking.chestnut.models.CreditType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/creditType")
public class CreditTypeController {

    @Autowired
    CreditTypeService creditTypeService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<CreditType> getCreditTypeById(@PathVariable Long id){
        try {
            CreditType creditType = creditTypeService.getById(id);
            return ResponseEntity.ok().body(creditType);
        } catch(NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }

}
