package com.banking.chestnut.moneytransfers.controllers;

import com.banking.chestnut.models.ResponseObject;
import com.banking.chestnut.moneytransfers.DTO.DirectDebitDTO;
import com.banking.chestnut.moneytransfers.services.DirectDebitService;
import com.banking.chestnut.moneytransfers.services.TransfersAccountService;
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
@RequestMapping(path="/directDebits")
public class DirectDebitController {

    private final DirectDebitService directDebitService;
    private final TransfersAccountService transfersAccountService;

    private static ObjectMapper mapper = new ObjectMapper();

    //providerID means clientId (assuming provider is bank's client)
    @GetMapping("/provider/{providerId}")
    public ResponseEntity findByProviderId(@PathVariable("providerId") final int providerId) {
        List<DirectDebitDTO> directDebitsDTO = directDebitService.findByProviderId(transfersAccountService.findByClientId(providerId).getId());
        JsonNode returnData = mapper.valueToTree(directDebitsDTO);
        if (directDebitsDTO.isEmpty())
            return new ResponseEntity(ResponseObject.createError("No content"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(ResponseObject.createSuccess("", returnData), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable("id") final int id) {
        DirectDebitDTO dto = directDebitService.findById(id);
        JsonNode returnData = mapper.valueToTree(dto);
        if (dto == null)
            return new ResponseEntity(ResponseObject.createError("No content"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(ResponseObject.createSuccess("", returnData), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity addDirectDebit(@RequestBody DirectDebitDTO directDebitDTO) {
        directDebitService.addDirectDebit(directDebitDTO);
        return new ResponseEntity<>(ResponseObject.createSuccess("Direct debit created"), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity cancelDirectDebit(@PathVariable("id") final int id, @RequestBody DirectDebitDTO directDebitDTO) {
        try {
            directDebitService.cancelDirectDebit(id);
            return new ResponseEntity<>(ResponseObject.createSuccess(""), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ResponseObject.createError("Error during canceling direct debit"), HttpStatus.BAD_REQUEST);
        }
    }
}
