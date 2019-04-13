package com.banking.chestnut.depositControllers;

import com.banking.chestnut.depositServices.CapitalizationService;
import com.banking.chestnut.models.deposit.Capitalization;
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
    public ResponseEntity<Capitalization> getCapitalizationById(Integer id){
        try {
            Capitalization capitalization =capitalizationService.getCapitalizationById(id);
            return ResponseEntity.ok().body(capitalization);
        } catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }
}
