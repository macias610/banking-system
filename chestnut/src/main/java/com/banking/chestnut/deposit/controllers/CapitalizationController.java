package com.banking.chestnut.deposit.controllers;

import com.banking.chestnut.deposit.services.CapitalizationService;
import com.banking.chestnut.models.DepositCapitalizations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/capitalization")
public class CapitalizationController {
    
    @Autowired
    CapitalizationService capitalizationService;
    
    @GetMapping("/{id}")
    public ResponseEntity<DepositCapitalizations> getCapitalizationById(Integer id) {
        try {
            DepositCapitalizations depositCapitalizations = capitalizationService.getCapitalizationById(id);
            return ResponseEntity.ok().body(depositCapitalizations);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
