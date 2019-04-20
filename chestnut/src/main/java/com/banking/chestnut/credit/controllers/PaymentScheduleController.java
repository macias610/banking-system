package com.banking.chestnut.credit.controllers;

import com.banking.chestnut.credit.services.PaymentScheduleService;
import com.banking.chestnut.models.PaymentSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/paymentSchedule")
public class PaymentScheduleController {

    @Autowired
    PaymentScheduleService paymentScheduleService;

    UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();

    @GetMapping(value = "/{id}")
    public ResponseEntity<PaymentSchedule> getPaymentScheduleById(@PathVariable Integer id){
        try {
            PaymentSchedule paymentSchedule = paymentScheduleService.getById(id);
            return ResponseEntity.ok().body(paymentSchedule);
        } catch(NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<PaymentSchedule> addCredit(@RequestBody PaymentSchedule paymentSchedule) {
        try {
            PaymentSchedule createdPaymentSchedule = paymentScheduleService.addPaymentSchedule(paymentSchedule);
            UriComponents uriComponents = uriBuilder.fromPath("/paymentSchedule/{id}").buildAndExpand(createdPaymentSchedule.getId());
            return ResponseEntity.created(uriComponents.toUri()).body(createdPaymentSchedule);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }



}
