package com.banking.chestnut.credit.controllers;

import com.banking.chestnut.credit.dto.PaymentScheduleDto;
import com.banking.chestnut.credit.services.CreditService;
import com.banking.chestnut.credit.services.PaymentScheduleService;
import com.banking.chestnut.models.PaymentSchedule;
import com.banking.chestnut.models.ResponseObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static com.banking.chestnut.credit.helpers.JsonNodeCreator.createJsonNodeFrom;
import static com.banking.chestnut.models.ResponseObject.createError;
import static com.banking.chestnut.models.ResponseObject.createSuccess;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/paymentSchedule")
public class PaymentScheduleController {

    private static ObjectMapper mapper = new ObjectMapper();
    private static CreditService creditService;

    @Autowired
    PaymentScheduleService paymentScheduleService;

    @GetMapping("/{id}")
    public ResponseEntity getPaymentScheduleById(@PathVariable Integer id) {
    try {
        PaymentSchedule paymentSchedule = paymentScheduleService.getPaymentScheduleById(id);
        JsonNode returnData = mapper.valueToTree(paymentSchedule);
        return new ResponseEntity<>(ResponseObject.createSuccess("", returnData), HttpStatus.OK);
    } catch (NoSuchElementException e) {
        return new ResponseEntity<>(ResponseObject.createError("PAYMENT SCHEDULE NOT FOUND"), HttpStatus.NOT_FOUND);
    }
}

    @GetMapping("/creditId/{id}")
    ResponseEntity getAllPaymentSchedulesByCreditId(@PathVariable Integer id) {
        try {
//            List<PaymentSchedule> paymentSchedules = paymentScheduleService.getAllPaymentSchedulesByCreditId(id);
            Set<PaymentScheduleDto> paymentSchedules = paymentScheduleService.getAllPaymentSchedulesByCreditId(id);
//            JsonNode returnData = mapper.valueToTree(paymentSchedules);
//            return new ResponseEntity<>(ResponseObject.createSuccess("", returnData), HttpStatus.OK);
            ResponseObject success = createSuccess("", createJsonNodeFrom(paymentSchedules));
            return ResponseEntity.ok().body(success);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(ResponseObject.createError("CAN'T FIND CREDIT ID "), HttpStatus.BAD_REQUEST);
        }
    }
}
