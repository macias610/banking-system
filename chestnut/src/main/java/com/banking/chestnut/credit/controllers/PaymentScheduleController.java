package com.banking.chestnut.credit.controllers;

import com.banking.chestnut.credit.services.PaymentScheduleService;
import com.banking.chestnut.models.PaymentSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/paymentSchedule")
public class PaymentScheduleController {

    @Autowired
    PaymentScheduleService paymentScheduleService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<PaymentSchedule> getPaymentScheduleById(@PathVariable Long id){
        try {
            PaymentSchedule paymentSchedule = paymentScheduleService.getById(id);
            return ResponseEntity.ok().body(paymentSchedule);
        } catch(NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }



}
