package com.banking.chestnut.ror.controllers;

import com.banking.chestnut.models.Account;
import com.banking.chestnut.models.ResponseObject;
import com.banking.chestnut.models.Transaction;
import com.banking.chestnut.ror.dto.OverflowDto;
import com.banking.chestnut.ror.services.IAccountInfoService;
import com.banking.chestnut.ror.services.IAccountService;
import com.banking.chestnut.ror.services.ITransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/transaction")
public class TransactionController {

    private static ObjectMapper mapper = new ObjectMapper();

    private IAccountService accountService;

    private IAccountInfoService accountInfoService;

    private ITransactionService transactionService;

    @Autowired
    public TransactionController(IAccountService accountService, IAccountInfoService accountInfoService, ITransactionService transactionService) {
        this.accountService = accountService;
        this.accountInfoService= accountInfoService;
        this.transactionService = transactionService;
    }

    @PostMapping(value = "/overflow")
    @ResponseBody
    ResponseEntity registerOverflow(@RequestBody OverflowDto overflowDto){
        try {
            if(overflowDto.getValue() <= 0)
                return new ResponseEntity<>(ResponseObject.createError("Invalid amount"), HttpStatus.BAD_REQUEST);
            if(!Arrays.asList("CASH_IN", "CASH_OUT").contains(overflowDto.getType()))
                return new ResponseEntity<>(ResponseObject.createError("Invalid type operation"), HttpStatus.BAD_REQUEST);
            Optional<Account> account = this.accountService.getById(overflowDto.getAccountId());
            if(!account.isPresent())
                return new ResponseEntity<>(ResponseObject.createError("Account not found"), HttpStatus.NOT_FOUND);
            if(overflowDto.getType().equals("CASH_IN")){
                account.get().getInfoId().setAvailableAmount(account.get().getInfoId().getAvailableAmount() + overflowDto.getValue());
                this.accountInfoService.saveAccountInfo(account.get().getInfoId());
                Transaction transaction = prepareTransaction(overflowDto,  null, account.get());
                this.transactionService.saveTransaction(transaction);
            }
            if(overflowDto.getType().equals("CASH_OUT")){
                if((account.get().getInfoId().getAvailableAmount() - overflowDto.getValue()) + account.get().getInfoId().getLockedAmount() < 0)
                    return new ResponseEntity(ResponseObject.createError("Insufficient funds on sender account"), HttpStatus.BAD_REQUEST);
                account.get().getInfoId().setAvailableAmount(account.get().getInfoId().getAvailableAmount() - overflowDto.getValue());
                this.accountInfoService.saveAccountInfo(account.get().getInfoId());
                Transaction transaction = prepareTransaction(overflowDto,  account.get(),null);
                this.transactionService.saveTransaction(transaction);
            }
            return new ResponseEntity(ResponseObject.createSuccess(overflowDto.getType() + " Transaction registered"), HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(ResponseObject.createError("Error during saving overflow"), HttpStatus.BAD_REQUEST);
        }
    }

    private Transaction prepareTransaction(OverflowDto overflowDto, Account sender, Account receiver){
        Transaction transaction = new Transaction();
        transaction.setType(overflowDto.getType());
        transaction.setIsTransferClientAcconuts(false);
        transaction.setIsForeign(false);
        transaction.setIsViaBank(false);
        if(overflowDto.getType().equals("CASH_IN"))
            transaction.setTitle("Cash " + overflowDto.getType() + " to " + receiver.getNumberBankingAccount());
        else if(overflowDto.getType().equals("CASH_OUT"))
            transaction.setTitle("Cash " + overflowDto.getType() + " from " + sender.getNumberBankingAccount());
        transaction.setValue(overflowDto.getValue());
        transaction.setSenderId(sender);
        transaction.setReceiverId(receiver);
        return transaction;
    }
}
