package com.banking.chestnut.credit.controllers;

import com.banking.chestnut.credit.dto.CreditDto;
import com.banking.chestnut.credit.services.CreditService;
import com.banking.chestnut.models.CreditType;
import com.banking.chestnut.models.Credits;
import com.banking.chestnut.models.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.NoSuchElementException;

import static com.banking.chestnut.credit.helpers.HateoasHelper.getUriWithPathAndParams;
import static com.banking.chestnut.credit.helpers.JsonNodeCreator.createJsonNodeFrom;
import static com.banking.chestnut.credit.helpers.Messages.ADD_CREDIT_ERROR;
import static com.banking.chestnut.credit.helpers.Messages.ADD_CREDIT_SUCCESS;
import static com.banking.chestnut.models.ResponseObject.createError;
import static com.banking.chestnut.models.ResponseObject.createSuccess;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/credit")
public class CreditController {

    @Autowired
    CreditService creditService;

    @GetMapping(value = "/{id}")
    ResponseEntity getCreditById(@PathVariable Integer id) {
        try {
            CreditDto credit = creditService.getCreditById(id);
            ResponseObject responseBody = createSuccess("", createJsonNodeFrom(credit));
            return ResponseEntity.ok().body(responseBody);
        } catch (NoSuchElementException e) {
            ResponseObject error = createError(e.getMessage());
            return ResponseEntity.status(NOT_FOUND).body(error);
        }
    }

    @PostMapping()
    public ResponseEntity addCredit(@RequestBody CreditDto creditDto) {
        try {
            creditService.addCredit(creditDto);
            String successMessage = String.valueOf(ADD_CREDIT_SUCCESS);
            return  new ResponseEntity<>(ResponseObject.createSuccess(successMessage), HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
            String errorMessage = String.valueOf(ADD_CREDIT_ERROR);
            return new ResponseEntity<>(ResponseObject.createError(errorMessage), HttpStatus.BAD_REQUEST);
        }
    }

}
