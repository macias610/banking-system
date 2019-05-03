package com.banking.chestnut.credit.controllers;

import com.banking.chestnut.credit.helpers.HateoasHelper;
import com.banking.chestnut.credit.services.CreditTypeService;
import com.banking.chestnut.models.CreditType;
import com.banking.chestnut.models.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static com.banking.chestnut.credit.helpers.JsonNodeCreator.createJsonNodeFrom;
import static com.banking.chestnut.credit.helpers.Messages.*;
import static com.banking.chestnut.models.ResponseObject.createError;
import static com.banking.chestnut.models.ResponseObject.createSuccess;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_MODIFIED;

@RestController
@RequestMapping("/creditType")
public class CreditTypeController {

    @Autowired
    CreditTypeService creditTypeService;

    @GetMapping("/{id}")
    public ResponseEntity getCreditTypeById(@PathVariable Integer id) {
    try {
        CreditType creditType = creditTypeService.getCreditTypeById(id);
        ResponseObject success = createSuccess("", createJsonNodeFrom(creditType));
        return ResponseEntity.ok().body(success);
    } catch (NoSuchElementException e) {
        ResponseObject error = createError(e.getMessage());
        return ResponseEntity.status(NOT_FOUND).body(error);
    }
    }

    @GetMapping()
    ResponseEntity getAllCreditTypes() {
        try {
            List<CreditType> creditType = creditTypeService.getAllCreditTypes();
            ResponseObject success = ResponseObject.createSuccess("",createJsonNodeFrom(creditType));
            return ResponseEntity.ok().body(success);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity addCreditType(@RequestBody CreditType creditType) {

        try {
            CreditType addedCreditType = creditTypeService.addCreditType(creditType);
            URI uriForFetchingCreatedDepositType = HateoasHelper.getUriWithPathAndParams("/creditType/{id}",addedCreditType.getId());
            String successMessage = String.valueOf(ADD_CREDIT_TYPE_SUCCESS);
            ResponseObject success = ResponseObject.createSuccess("",createJsonNodeFrom(addedCreditType));
            return ResponseEntity.created(uriForFetchingCreatedDepositType).body(success);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            String errorMessage = String.valueOf(CREATE_CREDIT_TYPE_ERROR);
            ResponseObject error = ResponseObject.createError(errorMessage);
            return ResponseEntity.badRequest().body(error);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCreditTypeById(@PathVariable Integer id) {
        try {
            creditTypeService.deleteCreditTypeById(id);
            String successMessage = String.valueOf(DELETE_CREDIT_TYPE_SUCCESS);
            ResponseObject success = ResponseObject.createSuccess("",createJsonNodeFrom(successMessage));
            return ResponseEntity.ok().body(success);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
        }
        }

}
