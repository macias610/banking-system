package com.banking.chestnut.moneytransfers.services;

import com.banking.chestnut.models.Account;
import com.banking.chestnut.models.AccountInfo;
import com.banking.chestnut.models.ClientInfo;
import com.banking.chestnut.moneytransfers.DTO.AccountDTO;
import com.banking.chestnut.moneytransfers.repositories.TransfersAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransfersAccountService {

    private final TransfersAccountRepository transfersAccountRepository;

    public Account findByClientId(int clientId) {
        return transfersAccountRepository.findByClientId_Id(clientId);
    }

    public Account findById(int id) {
        return transfersAccountRepository.findById(id);
    }

    @Transactional
    public Account updateAvailableAmount(int accountId, long amount) {
        Account account = transfersAccountRepository.findById(accountId);
        AccountInfo accountInfo = account.getInfoId();
        accountInfo.setAvailableAmount(accountInfo.getAvailableAmount() + amount);
        account.setInfoId(accountInfo);
        return transfersAccountRepository.save(account);
    }

    public Account findByClientNumber(String number) {
        return transfersAccountRepository.findByNumberBankingAccount(number);
    }

    public boolean checkIfAccountExists(String number) {
        Account account = transfersAccountRepository.findByNumberBankingAccount(number);
        if (account == null)
            return false;
        else
            return true;
    }

    public List<AccountDTO> findByType(String type) {
        List<Account> accounts = transfersAccountRepository.findByType(type);
        List<AccountDTO> accountDTOs = new ArrayList<>();
        for(Account a: accounts) {
            accountDTOs.add(prepareModel(a));
        }
        return accountDTOs;
    }

    private AccountDTO prepareModel(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        ClientInfo clientInfo = account.getClientId().getClientInfoId();
        accountDTO.setId(account.getId());
        accountDTO.setAccountNumber(account.getNumberBankingAccount());
        accountDTO.setType(account.getType());
        accountDTO.setClientName(clientInfo.getFirstName());
        accountDTO.setClientSurname(clientInfo.getSurname());
        return accountDTO;
    }
}
