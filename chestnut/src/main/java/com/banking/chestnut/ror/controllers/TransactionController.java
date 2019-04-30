package com.banking.chestnut.ror.controllers;

import com.banking.chestnut.models.Account;
import com.banking.chestnut.models.ResponseObject;
import com.banking.chestnut.models.Transaction;
import com.banking.chestnut.ror.dto.OverflowDto;
import com.banking.chestnut.ror.services.IAccountInfoService;
import com.banking.chestnut.ror.services.IAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/transaction")
public class TransactionController {

    private static ObjectMapper mapper = new ObjectMapper();

    private IAccountService accountService;

    private IAccountInfoService accountInfoService;

    @Autowired
    public TransactionController(IAccountService accountService, IAccountInfoService accountInfoService) {
        this.accountService = accountService;
        this.accountInfoService= accountInfoService;
    }

    @PostMapping(value = "/overflow/{accountId}")
    @ResponseBody
    ResponseEntity registerOverflow(@RequestBody OverflowDto overflowDto, @PathVariable Integer accountId){
        try {
            Optional<Account> account = this.accountService.getById(accountId);
            if(!account.isPresent())
                return new ResponseEntity<>(ResponseObject.createError("Account not found"), HttpStatus.NOT_FOUND);
            Account originalDb = account.get();
            if(overflowDto.getValue() <= 0)
                return new ResponseEntity<>(ResponseObject.createError("Invalid amount"), HttpStatus.BAD_REQUEST);
            if(overflowDto.getType().equals("OUT")){
                if((originalDb.getInfoId().getAvailableAmount() - overflowDto.getValue()) + originalDb.getInfoId().getLockedAmount() < 0)
                    return new ResponseEntity(ResponseObject.createError("Insufficient funfs on account"), HttpStatus.BAD_REQUEST);
                originalDb.getInfoId().setAvailableAmount(originalDb.getInfoId().getAvailableAmount() - overflowDto.getValue());
                this.accountInfoService.saveAccountInfo(originalDb.getInfoId());
                Transaction transaction = new Transaction();
                transaction.setType(overflowDto.getType());
//                transaction.setIsTransferClientAcconuts();
                transaction.setValue(overflowDto.getValue());
            }
            return new ResponseEntity(ResponseObject.createSuccess("OK"), HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(ResponseObject.createError("Error during saving overflow"), HttpStatus.BAD_REQUEST);
        }

    }
}
