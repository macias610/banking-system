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

    @PostMapping(value = "/overflow/{accountId}")
    @ResponseBody
    ResponseEntity registerOverflow(@RequestBody OverflowDto overflowDto, @PathVariable Integer accountId){
        try {
            Optional<Account> sender = this.accountService.getById(accountId);
            if(!sender.isPresent())
                return new ResponseEntity<>(ResponseObject.createError("Sender account not found"), HttpStatus.NOT_FOUND);
            Optional<Account> receiver = this.accountService.getById(overflowDto.getReceiverId());
            if(!receiver.isPresent())
                return new ResponseEntity<>(ResponseObject.createError("Receiver account not found"), HttpStatus.NOT_FOUND);
            if(overflowDto.getValue() <= 0)
                return new ResponseEntity<>(ResponseObject.createError("Invalid amount"), HttpStatus.BAD_REQUEST);

            List<Integer> clientAccountsIds = this.accountService.
                    getClientAccounts(sender.get().getClientId().getId()).stream().map(item -> item.getId()).collect(Collectors.toList());

            if(overflowDto.getType().equals("OUT")){
                if((sender.get().getInfoId().getAvailableAmount() - overflowDto.getValue()) + sender.get().getInfoId().getLockedAmount() < 0)
                    return new ResponseEntity(ResponseObject.createError("Insufficient funds on sender account"), HttpStatus.BAD_REQUEST);
            }
            sender.get().getInfoId().setAvailableAmount(sender.get().getInfoId().getAvailableAmount() - overflowDto.getValue());
            this.accountInfoService.saveAccountInfo(sender.get().getInfoId());
            receiver.get().getInfoId().setAvailableAmount(receiver.get().getInfoId().getAvailableAmount() + overflowDto.getValue());
            this.accountInfoService.saveAccountInfo(receiver.get().getInfoId());
            Transaction transaction = prepareTransaction(overflowDto, clientAccountsIds, sender.get(), receiver.get());
            this.transactionService.saveTransaction(transaction);
            return new ResponseEntity(ResponseObject.createSuccess("OK"), HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity(ResponseObject.createError("Error during saving overflow"), HttpStatus.BAD_REQUEST);
        }
    }

    private Transaction prepareTransaction(OverflowDto overflowDto, List<Integer> clientAccountsIds, Account sender, Account receiver){
        Transaction transaction = new Transaction();
        transaction.setType(overflowDto.getType());
        transaction.setIsTransferClientAcconuts(clientAccountsIds.contains(overflowDto.getReceiverId())
                && clientAccountsIds.contains(overflowDto.getSenderId()));
        transaction.setIsForeign(!sender.getIban().replaceAll("[^a-zA-Z].*", "").
                equals(receiver.getIban().replaceAll("[^a-zA-Z].*", "")));
        transaction.setIsViaBank(!sender.getClientId().getBankId().getSwift().equals(receiver.getClientId().getBankId().getSwift()));
        transaction.setTitle("Overflow from " + sender.getNumberBankingAccount() + " to " + receiver.getNumberBankingAccount());
        transaction.setValue(overflowDto.getValue());
        transaction.setSenderId(sender);
        transaction.setReceiverId(receiver);
        return transaction;
    }
}
