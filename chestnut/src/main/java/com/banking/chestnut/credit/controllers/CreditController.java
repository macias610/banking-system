package com.banking.chestnut.credit.controllers;

import com.banking.chestnut.credit.services.CreditService;
import com.banking.chestnut.models.Credits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/credit")
public class CreditController {
    @Autowired
    CreditService creditService;

    UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

    @GetMapping(value = "/{id}")
    public ResponseEntity<Credits> getCreditById(@PathVariable Long id){
        try {
            Credits credits = creditService.getById(id);
            return ResponseEntity.ok().body(credits);
        } catch(NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }
}
