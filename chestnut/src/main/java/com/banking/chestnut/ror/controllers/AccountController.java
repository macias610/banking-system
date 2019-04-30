package com.banking.chestnut.ror.controllers;

import com.banking.chestnut.models.*;
import com.banking.chestnut.ror.dto.*;
import com.banking.chestnut.ror.services.IAccountInfoService;
import com.banking.chestnut.ror.services.IAccountService;
import com.banking.chestnut.ror.services.ICardService;
import com.banking.chestnut.ror.services.IClientService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    private IAccountInfoService accountInfoService;

    private ICardService cardService;

    private IClientService clientService;

    private IAccountService accountService;

    private static ObjectMapper mapper = new ObjectMapper();

    private static ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public AccountController(IAccountInfoService accountInfoService, IClientService clientService,
                             IAccountService accountService, ICardService cardService) {
        this.accountInfoService = accountInfoService;
        this.clientService = clientService;
        this.accountService = accountService;
        this.cardService = cardService;
    }

    @PostMapping (value = "/save")
    @ResponseBody
    ResponseEntity saveClientAccount(@RequestBody AccountSaveDto accountSaveDto){
        try {
            Optional<Client> client = this.clientService.getById(accountSaveDto.getClientId());
            if(!client.isPresent())
                return new ResponseEntity<>(ResponseObject.createError("Client not found"), HttpStatus.NOT_FOUND);
            AccountInfo accountInfo = new AccountInfo();
            this.accountInfoService.saveAccountInfo(accountInfo);
            Account account = new Account(accountSaveDto.getCurrency());
            account.setInfoId(accountInfo);
            this.accountService.saveAccount(account, client.get());
            return  new ResponseEntity<>(ResponseObject.createSuccess("Account for user " + client.get().getClientInfoId().getSurname() + " created"), HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResponseObject.createError("Error during create account"), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(value = "/state/{accountId}")
    @ResponseBody
    ResponseEntity lockClientAccount(@PathVariable Integer accountId){
        try {
            Optional<Account> account = this.accountService.getById(accountId);
            if(!account.isPresent())
                return new ResponseEntity<>(ResponseObject.createError("Account not found"), HttpStatus.NOT_FOUND);
            Account originalDb = account.get();
            originalDb.setIsBlocked(originalDb.getIsBlocked().equals(true)? false : true);
            this.accountService.editAccount(originalDb);
            List<Card> cards = this.cardService.getByAccountId(originalDb);
            for(Card card : cards){
                card.setStatus(originalDb.getIsBlocked().equals(true)? false : true);
                this.cardService.saveCard(card);
            }
            return new ResponseEntity<>(ResponseObject.createSuccess("Account locked"), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResponseObject.createError("Error during locking account"), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping(value = "/delete/{accountId}")
    @ResponseBody
    ResponseEntity deleteAccount(@PathVariable Integer accountId){
        try {
            Optional<Account> account = this.accountService.getById(accountId);
            if(!account.isPresent())
                return new ResponseEntity<>(ResponseObject.createError("Account not found"), HttpStatus.NOT_FOUND);
            Account originalDb = account.get();
            this.accountService.deleteAccount(originalDb);
            List<Card> cards = this.cardService.getByAccountId(originalDb);
            for(Card card : cards){
                card.setStatus(false);
                this.cardService.saveCard(card);
            }
            return new ResponseEntity<>(ResponseObject.createSuccess("Account deleted"), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResponseObject.createError("Error during deleting account"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    ResponseEntity showSingleAccount(@PathVariable Integer id){
        try {
            Optional<Account> account = this.accountService.getById(id);
            if (account.isPresent()){
                JsonNode returnData = mapper.valueToTree(new AccountDto(account.get()));
                return new ResponseEntity<>(ResponseObject.createSuccess("", returnData), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(ResponseObject.createError("ACCOUNT NOT EXISTS"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResponseObject.createError("Error during fetch accounts data"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/all")
    @ResponseBody
    ResponseEntity getAllAccounts(){
        try {
            List<Account> accounts = this.accountService.getAll();
            List<AccountDto> accountDtos = new ArrayList<>();
            accounts.forEach(item -> accountDtos.add(new AccountDto(item)));
            JsonNode returnData = mapper.valueToTree(accountDtos);
            return new ResponseEntity<>(ResponseObject.createSuccess("", returnData), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResponseObject.createError("Error during fetch accounts data"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/client/{id}")
    @ResponseBody
    ResponseEntity getAccountsPerClient(@PathVariable Integer id){
        try {
            Optional<Client> client = this.clientService.getById(id);
            if(!client.isPresent())
                return new ResponseEntity<>(ResponseObject.createError("Client not found"), HttpStatus.NOT_FOUND);
            List<AccountDto> clientAccountDtos = new ArrayList<>();
            List<Account> accounts = this.accountService.getAll();
            accounts = accounts.stream().filter(item -> item.getClientId().getId().equals(id)).collect(Collectors.toList());
            accounts.forEach(item -> clientAccountDtos.add(new AccountDto(item)));
            JsonNode returnData = mapper.valueToTree(clientAccountDtos);
            return new ResponseEntity<>(ResponseObject.createSuccess("", returnData), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResponseObject.createError("Error during fetch client accounts"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/transactions/{accountId}")
    @ResponseBody
    ResponseEntity getAccountTransactions(@PathVariable Integer accountId, @RequestBody TransactionDto transactionDto){
        try {
            Optional<Account> account = this.accountService.getById(accountId);
            if(!account.isPresent())
                return new ResponseEntity<>(ResponseObject.createError("Account not found"), HttpStatus.NOT_FOUND);
            List<Transaction> transactions = this.accountService.getTransactionsByAccount(transactionDto, accountId);
            List<TransactionAccountDto> transactionAccountDtos = new ArrayList<>();
            for(Transaction transaction : transactions){
                TransactionAccountDto transactionAccountDto = modelMapper.map(transaction, TransactionAccountDto.class);
                if(transaction.getSenderId() != null)
                    transactionAccountDto.setSender(modelMapper.map(transaction.getSenderId(), TransactionSideDto.class));
                else
                    transactionAccountDto.setSender(null);
                if(transaction.getReceiverId() != null)
                    transactionAccountDto.setReceiver(modelMapper.map(transaction.getReceiverId(), TransactionSideDto.class));
                else
                    transactionAccountDto.setReceiver(null);
                transactionAccountDtos.add(transactionAccountDto);
            }
            JsonNode jsonNode = mapper.valueToTree(transactionAccountDtos);
            return new ResponseEntity<>(ResponseObject.createSuccess("", jsonNode), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResponseObject.createError("Error during fetching transaction for account " + accountId), HttpStatus.BAD_REQUEST);
        }
    }
}
