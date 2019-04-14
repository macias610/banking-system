package com.banking.chestnut.moneytransfers.controllers;

import com.banking.chestnut.models.Transactions;
import com.banking.chestnut.moneytransfers.DTO.TransactionDTO;
import com.banking.chestnut.moneytransfers.services.AccountService;
import com.banking.chestnut.moneytransfers.services.TransactionService;
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
    private final AccountService accountService;

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<TransactionDTO>> findByClientId(@PathVariable("clientId") final int clientId) {
        List<TransactionDTO> transactionsDTO = transactionService.findAllByReceiverIdOrSenderId(accountService.findByClientId(clientId).getId());
        if (transactionsDTO.isEmpty())
            return new ResponseEntity(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(transactionsDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionDTO> findById(@PathVariable("id") final int id) {
        TransactionDTO dto = transactionService.findById(id);
        if (dto == null)
            return new ResponseEntity(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Transactions> createTransactions(@RequestBody TransactionDTO transactionDTO) {
        Transactions outgoing = transactionService.addTransaction(transactionDTO, "outgoing");
        Transactions incoming = transactionService.addTransaction(transactionDTO, "incoming");
        accountService.updateAvailableAmount(outgoing.getSenderId().getId(), -outgoing.getValue());
        accountService.updateAvailableAmount(incoming.getReceiverId().getId(), incoming.getValue());
        return new ResponseEntity<>(outgoing, HttpStatus.CREATED);
    }
}
