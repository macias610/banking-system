package com.banking.chestnut.moneytransfers.controllers;

import com.banking.chestnut.models.PermanentTransactions;
import com.banking.chestnut.models.ResponseObject;
import com.banking.chestnut.moneytransfers.DTO.PermanentTransactionDTO;
import com.banking.chestnut.moneytransfers.services.TransfersAccountService;
import com.banking.chestnut.moneytransfers.services.PermanentTransactionService;
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
@RequestMapping(path="/permanentTransactions")
public class PermanentTransactionController {

    private final PermanentTransactionService permanentTransactionService;
    private final TransfersAccountService transfersAccountService;

    private static ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/client/{clientId}")
    public ResponseEntity findByClientId(@PathVariable("clientId") final int clientId) {
        List<PermanentTransactionDTO> transactionsDTO = permanentTransactionService.findBySenderIdOrReceiverId(transfersAccountService.findByClientId(clientId).getId());
        JsonNode returnData = mapper.valueToTree(transactionsDTO);
        if (transactionsDTO.isEmpty())
            return new ResponseEntity(ResponseObject.createError("No content"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(ResponseObject.createSuccess("", returnData), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") final int id) {
        PermanentTransactionDTO dto = permanentTransactionService.findById(id);
        JsonNode returnData = mapper.valueToTree(dto);
        if (dto == null)
            return new ResponseEntity(ResponseObject.createError("No content"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(ResponseObject.createSuccess("", returnData), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity createPermanentTransaction(@RequestBody PermanentTransactionDTO dto) {
        dto.setReceiverAccNumber(dto.getReceiverAccNumber().replace(" ", "").trim());
        dto.setSenderAccNumber(dto.getSenderAccNumber().replace(" ", "").trim());
        if (transfersAccountService.checkIfAccountExists(dto.getReceiverAccNumber())) {
            if (dto.getValue() < 0) {
                JsonNode returnData = mapper.valueToTree(dto);
                return new ResponseEntity(ResponseObject.createError("Value < 0", returnData), HttpStatus.BAD_REQUEST);
            } else {
                permanentTransactionService.addPermanentTransaction(dto);
                return new ResponseEntity<>(ResponseObject.createSuccess("Transactions created"), HttpStatus.CREATED);
            }
        } else {
            JsonNode returnData = mapper.valueToTree(dto);
            return new ResponseEntity(ResponseObject.createError("Account does not exist", returnData), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity cancelPermanentTransaction(@PathVariable("id") final int id) {
        try {
            permanentTransactionService.cancelPermanentTransaction(id);
            return new ResponseEntity<>(ResponseObject.createSuccess(""), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResponseObject.createError("Error during canceling permanent transaction"), HttpStatus.BAD_REQUEST);
        }
    }
}