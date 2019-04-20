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
    public ResponseEntity<Credits> getCreditById(@PathVariable Integer id){
        try {
            Credits credits = creditService.getById(id);
            return ResponseEntity.ok().body(credits);
        } catch(NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }

    //do poprawy
    @PostMapping("/add")
    public ResponseEntity<Credits> addCredit(@RequestBody Credits credit) {
        try {
            Credits createdCredits = creditService.addCredit(credit);
            UriComponents uriComponents = uriBuilder.fromPath("/credit/{id}").buildAndExpand(createdCredits.getId());
            return ResponseEntity.created(uriComponents.toUri()).body(createdCredits);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
