package com.banking.chestnut.ror.controllers;

import com.banking.chestnut.models.AccountInfo;
import com.banking.chestnut.models.Account;
import com.banking.chestnut.models.Client;
import com.banking.chestnut.models.ResponseObject;
import com.banking.chestnut.ror.dto.AccountDto;
import com.banking.chestnut.ror.dto.AccountSaveDto;
import com.banking.chestnut.ror.services.IAccountInfoService;
import com.banking.chestnut.ror.services.IAccountService;
import com.banking.chestnut.ror.services.IClientService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/account")
public class AccountController {

    private IAccountInfoService accountInfoService;

    private IClientService clientService;

    private IAccountService accountService;

    private static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public AccountController(IAccountInfoService accountInfoService, IClientService clientService, IAccountService accountService) {
        this.accountInfoService = accountInfoService;
        this.clientService = clientService;
        this.accountService = accountService;
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
}
