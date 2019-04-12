package com.banking.chestnut.commoncontrollers;

import com.banking.chestnut.commonservices.IBankService;
import com.banking.chestnut.models.Banks;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/bank")
public class BankController {

    private IBankService bankService;
    private Gson gson;

    @Autowired
    public BankController(IBankService bankService) {
        this.bankService = bankService;
        this.gson = new Gson();
    }

    @GetMapping(value = "/info")
    @ResponseBody
    ResponseEntity getBankInfo(){
        try {
            Optional<Banks> bank = this.bankService.getBankInfo();
            if(bank.isPresent())
                return new ResponseEntity<>(gson.toJson(bank.get()), HttpStatus.OK);
            else
                return new ResponseEntity<>(gson.toJson("No bank info"), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(gson.toJson(e), HttpStatus.BAD_REQUEST);
        }
    }
}
