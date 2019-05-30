package com.banking.chestnut.credit.controllers;

import com.banking.chestnut.credit.dto.CreditTypeDto;
import com.banking.chestnut.credit.helpers.HateoasHelper;
import com.banking.chestnut.credit.services.CreditTypeService;
import com.banking.chestnut.models.CreditType;
import com.banking.chestnut.models.ResponseObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static com.banking.chestnut.credit.helpers.JsonNodeCreator.createJsonNodeFrom;
import static com.banking.chestnut.credit.helpers.Messages.*;
import static com.banking.chestnut.models.ResponseObject.createError;
import static com.banking.chestnut.models.ResponseObject.createSuccess;
import static java.lang.String.valueOf;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_MODIFIED;

@RestController
@RequestMapping("/creditType")
public class CreditTypeController {

    private static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    CreditTypeService creditTypeService;

    @GetMapping("/{id}")
    public ResponseEntity getCreditTypeById(@PathVariable Integer id) {
    try {
        CreditType creditType = creditTypeService.getCreditTypeById(id);
        JsonNode returnData = mapper.valueToTree(creditType);
        return new ResponseEntity<>(ResponseObject.createSuccess("", returnData), HttpStatus.OK);
    } catch (NoSuchElementException e) {
        ResponseObject error = createError(e.getMessage());
        return new ResponseEntity<>(ResponseObject.createError("CREDIT TYPE NOT FOUND"), HttpStatus.NOT_FOUND);
    }
    }

    @GetMapping()
    ResponseEntity getAllCreditTypes() {
        try {
            List<CreditType> creditType = creditTypeService.getAllCreditTypes();
            JsonNode returnData = mapper.valueToTree(creditType);
            return new ResponseEntity<>(ResponseObject.createSuccess("", returnData), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(ResponseObject.createError("Error during fetch credit type data "), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping()
    public ResponseEntity addCreditType(@RequestBody CreditType creditType) {

        try {
            CreditType addedCreditType = creditTypeService.addCreditType(creditType);
            String successMessage = String.valueOf(ADD_CREDIT_TYPE_SUCCESS);
            return  new ResponseEntity<>(ResponseObject.createSuccess(successMessage), HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            String errorMessage = String.valueOf(CREATE_CREDIT_TYPE_ERROR);
            return new ResponseEntity<>(ResponseObject.createError(errorMessage), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCreditTypeByIdOld(@PathVariable Integer id) {
        try {
            creditTypeService.deleteCreditTypeById(id);
            return new ResponseEntity<>(ResponseObject.createSuccess("Credit type deleted"), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(ResponseObject.createError("Credit type not found"), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity deleteCreditTypeById(@PathVariable Integer id) {
        try {
            CreditTypeDto creditType = creditTypeService.deleteCreditTypeById(id);
            String successMessage = valueOf(DELETE_CREDIT_TYPE_SUCCESS);
            ResponseObject success = createSuccess(successMessage, createJsonNodeFrom(creditType));
            return  ResponseEntity.ok().body(success);
        } catch (NoSuchElementException e) {
            ResponseObject error = ResponseObject.createError(e.getMessage());
            return  ResponseEntity.status(NOT_MODIFIED).body(error);
        }
    }

}
