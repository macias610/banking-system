package com.banking.chestnut.credit.controllers;

import com.banking.chestnut.credit.services.PaymentScheduleService;
import com.banking.chestnut.models.PaymentSchedule;
import com.banking.chestnut.models.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.NoSuchElementException;

import static com.banking.chestnut.credit.helpers.JsonNodeCreator.createJsonNodeFrom;
import static com.banking.chestnut.models.ResponseObject.createError;
import static com.banking.chestnut.models.ResponseObject.createSuccess;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/paymentSchedule")
public class PaymentScheduleController {

    @Autowired
    PaymentScheduleService paymentScheduleService;

    @GetMapping("/{id}")
    public ResponseEntity getPaymentScheduleById(@PathVariable Integer id) {
    try {
        PaymentSchedule paymentSchedule = paymentScheduleService.getPaymentScheduleById(id);
        ResponseObject success = createSuccess("", createJsonNodeFrom(paymentSchedule));
        return ResponseEntity.ok().body(success);
    } catch (NoSuchElementException e) {
        ResponseObject error = createError(e.getMessage());
        return ResponseEntity.status(NOT_FOUND).body(error);
    }
}



}
