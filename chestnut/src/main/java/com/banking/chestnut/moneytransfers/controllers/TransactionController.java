package com.banking.chestnut.moneytransfers.controllers;

import com.banking.chestnut.models.ResponseObject;
import com.banking.chestnut.models.Transactions;
import com.banking.chestnut.moneytransfers.DTO.TransactionDTO;
import com.banking.chestnut.moneytransfers.services.TransfersAccountService;
import com.banking.chestnut.moneytransfers.services.TransactionService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(path="/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final TransfersAccountService transfersAccountService;

    private static ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/client/{clientId}")
    public ResponseEntity findByClientId(@PathVariable("clientId") final int clientId) {
        List<TransactionDTO> transactionsDTO = transactionService.findAllByReceiverIdOrSenderId(transfersAccountService.findByClientId(clientId).getId());
        JsonNode returnData = mapper.valueToTree(transactionsDTO);
        if (transactionsDTO.isEmpty())
            return new ResponseEntity(ResponseObject.createError("No content"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(ResponseObject.createSuccess("", returnData), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") final int id) {
        TransactionDTO dto = transactionService.findById(id);
        JsonNode returnData = mapper.valueToTree(dto);
        if (dto == null)
            return new ResponseEntity(ResponseObject.createError("No content"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(ResponseObject.createSuccess("", returnData), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity createTransactions(@RequestBody TransactionDTO transactionDTO) {
        Transactions outgoing = transactionService.addTransaction(transactionDTO, "outgoing");
        Transactions incoming = transactionService.addTransaction(transactionDTO, "incoming");
        transfersAccountService.updateAvailableAmount(outgoing.getSenderId().getId(), -outgoing.getValue());
        transfersAccountService.updateAvailableAmount(incoming.getReceiverId().getId(), incoming.getValue());
        return new ResponseEntity<>(ResponseObject.createSuccess("Transactions created"), HttpStatus.CREATED);
    }
}
