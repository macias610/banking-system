package com.banking.chestnut.moneytransfers.controllers;

import com.banking.chestnut.models.PermanentTransactions;
import com.banking.chestnut.moneytransfers.DTO.PermanentTransactionDTO;
import com.banking.chestnut.moneytransfers.services.TransfersAccountService;
import com.banking.chestnut.moneytransfers.services.PermanentTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping(path="/permanentTransactions")
public class PermanentTransactionController {

    private final PermanentTransactionService permanentTransactionService;
    private final TransfersAccountService transfersAccountService;

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<PermanentTransactionDTO>> findByClientId(@PathVariable("clientId") final int clientId) {
        List<PermanentTransactionDTO> transactionsDTO = permanentTransactionService.findBySenderIdOrReceiverId(transfersAccountService.findByClientId(clientId).getId());
        if (transactionsDTO.isEmpty())
            return new ResponseEntity(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(transactionsDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermanentTransactionDTO> findById(@PathVariable("id") final int id) {
        PermanentTransactionDTO dto = permanentTransactionService.findById(id);
        if (dto == null)
            return new ResponseEntity(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<PermanentTransactions> createPermanentTransaction(@RequestBody PermanentTransactionDTO dto) {
        return new ResponseEntity<>(permanentTransactionService.addPermanentTransaction(dto), HttpStatus.CREATED);
    }
}
