package com.banking.chestnut.ror.controllers;

import com.banking.chestnut.commonservices.IUserService;
import com.banking.chestnut.helper.BCryptUtility;
import com.banking.chestnut.models.Account;
import com.banking.chestnut.models.Card;
import com.banking.chestnut.models.Client;
import com.banking.chestnut.models.ResponseObject;
import com.banking.chestnut.ror.dto.CardAccountDto;
import com.banking.chestnut.ror.dto.CardClientDto;
import com.banking.chestnut.ror.dto.CardDto;
import com.banking.chestnut.ror.dto.EditCardDto;
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

@RestController
@RequestMapping(value = "/card")
public class CardController {

    IAccountService accountService;

    IClientService clientService;

    ICardService cardService;

    private static ModelMapper modelMapper = new ModelMapper();

    private static ObjectMapper mapper = new ObjectMapper();

    @Autowired
    public CardController(IAccountService accountService, ICardService cardService, IClientService clientService) {
        this.accountService = accountService;
        this.cardService = cardService;
        this.clientService = clientService;
    }

    @PostMapping(value = "/save")
    @ResponseBody
    ResponseEntity saveCardAccount(@RequestBody CardDto cardDto){
        try{
            Optional<Account> account =  this.accountService.getById(cardDto.getAccountId());
            if(!account.isPresent())
                return new ResponseEntity<>(ResponseObject.createError("Account not found"), HttpStatus.NOT_FOUND);
            if(account.get().getIsBlocked())
                return new ResponseEntity<>(ResponseObject.createError("Account locked"), HttpStatus.OK);
            Card card = new Card();
            card.setAccountId(account.get());
            this.cardService.saveCard(card);
            return new ResponseEntity<>(ResponseObject.createSuccess("Saved card for account " + cardDto.getAccountId()), HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResponseObject.createError("Error during saving card account"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/edit")
    @ResponseBody
    ResponseEntity editCardAccount(@RequestBody EditCardDto editCardDto){
        try {
            Optional<Card> card = this.cardService.getById(editCardDto.getId());
            if(!card.isPresent())
                return new ResponseEntity<>(ResponseObject.createError("Card not found"), HttpStatus.NOT_FOUND);
            if(card.get().getAccountId().getIsBlocked())
                return new ResponseEntity<>(ResponseObject.createError("Account locked"), HttpStatus.OK);
            BCryptUtility bCryptUtility = new BCryptUtility(5);
            if(editCardDto.getType().toLowerCase().equals("pin"))
                card.get().setPin(bCryptUtility.hash(editCardDto.getValue()));
            else if(editCardDto.getType().toLowerCase().equals("status"))
                card.get().setStatus(Boolean.valueOf(editCardDto.getValue()));
            this.cardService.editCard(card.get());
            return new ResponseEntity<>(ResponseObject.createSuccess("Card " + editCardDto.getId() + " edited"), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResponseObject.createError("Cannot edit card account"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/account/{accountId}")
    @ResponseBody
    ResponseEntity getCardsByAccountId(@PathVariable Integer accountId){
        try{
            Optional<Account> account = this.accountService.getById(accountId);
            if(!account.isPresent())
                return new ResponseEntity<>(ResponseObject.createError("Account not found"), HttpStatus.NOT_FOUND);
            Account accountDb = account.get();
            List<Card> cards = accountDb.getCards();
            List<CardAccountDto> cardAccountDtos = new ArrayList<>();
            cards.forEach(item -> cardAccountDtos.add(modelMapper.map(item, CardAccountDto.class)));
            JsonNode jsonNode = mapper.valueToTree(cardAccountDtos);
            return new ResponseEntity<>(ResponseObject.createSuccess("", jsonNode), HttpStatus.OK);

        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResponseObject.createError("Error during fetching cards for account"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/client/{clientId}")
    @ResponseBody
    ResponseEntity getCardsByClientId(@PathVariable Integer clientId){
        try {
            Optional<Client> client = this.clientService.getById(clientId);
            if(!client.isPresent())
                return new ResponseEntity<>(ResponseObject.createError("Client not found"), HttpStatus.NOT_FOUND);
            List<CardClientDto> cardClientDtos = new ArrayList<>();
            Client clientDb = client.get();
            for(Account account : clientDb.getAccounts()){
                for(Card card : account.getCards()){
                    CardClientDto cardClientDto = modelMapper.map(card, CardClientDto.class);
                    cardClientDto.setNumberBankingAccount(account.getNumberBankingAccount());
                    cardClientDtos.add(cardClientDto);
                }
            }
            JsonNode jsonNode = mapper.valueToTree(cardClientDtos);
            return new ResponseEntity<>(ResponseObject.createSuccess("", jsonNode), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(ResponseObject.createError("Error during fetching cards for client"), HttpStatus.BAD_REQUEST);
        }
    }
}
