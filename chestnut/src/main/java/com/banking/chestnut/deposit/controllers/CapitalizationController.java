package com.banking.chestnut.deposit.controllers;

import com.banking.chestnut.deposit.services.CapitalizationService;
import com.banking.chestnut.models.DepositCapitalizations;
import com.banking.chestnut.models.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

import static com.banking.chestnut.deposit.helpers.JsonNodeCreator.createJsonNodeFrom;
import static com.banking.chestnut.models.ResponseObject.createError;
import static com.banking.chestnut.models.ResponseObject.createSuccess;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/capitalization")
public class CapitalizationController {
    
    @Autowired
    CapitalizationService capitalizationService;
    
    @GetMapping("/{id}")
    public ResponseEntity getCapitalizationById(@PathVariable Integer id) {
        try {
            DepositCapitalizations depositCapitalizations = capitalizationService.getCapitalizationById(id);
            ResponseObject success = createSuccess("", createJsonNodeFrom(depositCapitalizations));
            return ResponseEntity.ok().body(success);
        } catch (NoSuchElementException e) {
            ResponseObject error = createError(e.getMessage());
            return ResponseEntity.status(NOT_FOUND).body(error);
        }
    }
}
