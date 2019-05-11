package com.banking.chestnut.moneytransfers.controllers;

import com.banking.chestnut.models.Account;
import com.banking.chestnut.models.ResponseObject;
import com.banking.chestnut.models.Transaction;
import com.banking.chestnut.moneytransfers.DTO.TransactionDTO;
import com.banking.chestnut.moneytransfers.services.TransfersAccountService;
import com.banking.chestnut.moneytransfers.services.MoneyTransactionService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(path="/transactions")
public class MoneyTransactionController {

    private final MoneyTransactionService moneyTransactionService;
    private final TransfersAccountService transfersAccountService;

    private static ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/client/{clientId}")
    public ResponseEntity findByClientId(@PathVariable("clientId") final int clientId) {
        List<TransactionDTO> transactionsDTO = new ArrayList<>();
        for(Account account: transfersAccountService.findByClientId(clientId)) {
            transactionsDTO.addAll(moneyTransactionService.findAllBySenderId(account.getId()));
            transactionsDTO.addAll(moneyTransactionService.findAllByReceiverId(account.getId()));
        }
        JsonNode returnData = mapper.valueToTree(transactionsDTO);
        if (transactionsDTO.isEmpty())
            return new ResponseEntity(ResponseObject.createError("No content"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(ResponseObject.createSuccess("", returnData), HttpStatus.OK);
    }

//    @GetMapping("/sender/{senderId}")
//    public ResponseEntity findBySenderId(@PathVariable("senderId") final int senderId) {
//        List<TransactionDTO> transactionsDTO = moneyTransactionService.findAllBySenderId(transfersAccountService.findByClientId(senderId).getId());
//        JsonNode returnData = mapper.valueToTree(transactionsDTO);
//        if (transactionsDTO.isEmpty())
//            return new ResponseEntity(ResponseObject.createError("No content"), HttpStatus.NOT_FOUND);
//
//        return new ResponseEntity<>(ResponseObject.createSuccess("", returnData), HttpStatus.OK);
//    }
//
//    @GetMapping("/receiver/{receiverId}")
//    public ResponseEntity findByReceiverId(@PathVariable("receiverId") final int receiverId) {
//        List<TransactionDTO> transactionsDTO = moneyTransactionService.findAllByReceiverId(transfersAccountService.findByClientId(receiverId).getId());
//        JsonNode returnData = mapper.valueToTree(transactionsDTO);
//        if (transactionsDTO.isEmpty())
//            return new ResponseEntity(ResponseObject.createError("No content"), HttpStatus.NOT_FOUND);
//
//        return new ResponseEntity<>(ResponseObject.createSuccess("", returnData), HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") final int id) {
        TransactionDTO dto = moneyTransactionService.findById(id);
        JsonNode returnData = mapper.valueToTree(dto);
        if (dto == null)
            return new ResponseEntity(ResponseObject.createError("No content"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(ResponseObject.createSuccess("", returnData), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity createTransactions(@RequestBody TransactionDTO transactionDTO) {
        transactionDTO.setReceiverAccNumber(transactionDTO.getReceiverAccNumber().replace(" ", "").trim());
        transactionDTO.setSenderAccNumber(transactionDTO.getSenderAccNumber().replace(" ", "").trim());
        if (transfersAccountService.checkIfAccountExists(transactionDTO.getReceiverAccNumber())) {
            if (transactionDTO.getValue() < 0) {
                JsonNode returnData = mapper.valueToTree(transactionDTO);
                return new ResponseEntity(ResponseObject.createError("Value < 0", returnData), HttpStatus.BAD_REQUEST);
            } else {
                Transaction outgoing = moneyTransactionService.addTransaction(transactionDTO, "outgoing");
                Transaction incoming = moneyTransactionService.addTransaction(transactionDTO, "incoming");
                transfersAccountService.updateAvailableAmount(outgoing.getSenderId().getId(), outgoing.getValue());
                transfersAccountService.updateAvailableAmount(incoming.getReceiverId().getId(), incoming.getValue());
                return new ResponseEntity<>(ResponseObject.createSuccess("Transactions created"), HttpStatus.CREATED);
            }
        } else {
            JsonNode returnData = mapper.valueToTree(transactionDTO);
            return new ResponseEntity(ResponseObject.createError("Account does not exist", returnData), HttpStatus.BAD_REQUEST);
        }

    }
}
