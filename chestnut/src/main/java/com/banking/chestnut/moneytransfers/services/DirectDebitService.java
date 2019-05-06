package com.banking.chestnut.moneytransfers.services;

import com.banking.chestnut.models.DirectDebits;
import com.banking.chestnut.moneytransfers.DTO.DirectDebitDTO;
import com.banking.chestnut.moneytransfers.repositories.DirectDebitRepository;
import com.banking.chestnut.moneytransfers.repositories.TransfersAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class DirectDebitService {

    private final DirectDebitRepository directDebitRepository;
    private final TransfersAccountRepository transfersAccountRepository;

    public List<DirectDebitDTO> findByProviderId(int providerId){
        List<DirectDebits> directDebits = directDebitRepository.findByProviderId_IdAndIsEnabled(providerId, true);
        List<DirectDebitDTO> directDebitsDTO = new ArrayList<>();
        for (DirectDebits t: directDebits) {
            directDebitsDTO.add(prepareModel(t));
        }
        return directDebitsDTO;
    }

    public List<DirectDebitDTO> findByAccountId(int accountId) {
        List<DirectDebits> directDebits = directDebitRepository.findByAccountId_Id(accountId);
        List<DirectDebitDTO> directDebitsDTO = new ArrayList<>();
        for (DirectDebits t: directDebits) {
            directDebitsDTO.add(prepareModel(t));
        }
        return directDebitsDTO;
    }

    public DirectDebitDTO findById(int id) {
        return prepareModel(directDebitRepository.findById(id));
    }

    public DirectDebits addDirectDebit(DirectDebitDTO directDebitDTO) {
        DirectDebits directDebit = new DirectDebits();
        directDebit.setIsEnabled(true);
        directDebit.setAccountId(transfersAccountRepository.findByNumberBankingAccount(directDebitDTO.getClientAccNumber()));
        directDebit.setProviderId(transfersAccountRepository.findByNumberBankingAccount(directDebitDTO.getProviderAccNumber()));
        return directDebitRepository.save(directDebit);
    }

    public void cancelDirectDebit(int id) {
        DirectDebits directDebit = directDebitRepository.findById(id);
        directDebit.setIsEnabled(false);
        directDebitRepository.save(directDebit);
    }

    private DirectDebitDTO prepareModel(DirectDebits directDebit) {
        DirectDebitDTO dto = new DirectDebitDTO();
        dto.setId(directDebit.getId());
        dto.setClientAccNumber(directDebit.getAccountId().getNumberClientAccount());
        dto.setProviderAccNumber(directDebit.getProviderId().getNumberClientAccount());
        dto.setEnabled(directDebit.getIsEnabled());
        dto.setClientId(directDebit.getAccountId().getId());
        dto.setProviderId(directDebit.getProviderId().getId());
        return dto;
    }
}
