package com.banking.chestnut.moneytransfers.services;

import com.banking.chestnut.models.Account;
import com.banking.chestnut.models.AccountInfo;
import com.banking.chestnut.moneytransfers.repositories.TransfersAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransfersAccountService {

    private final TransfersAccountRepository transfersAccountRepository;

    public Account findByClientId(int clientId) {
        return transfersAccountRepository.findByClientId_Id(clientId);
    }

    @Transactional
    public Account updateAvailableAmount(int accountId, long amount) {
        Account account = transfersAccountRepository.findById(accountId);
        AccountInfo accountInfo = account.getInfoId();
        accountInfo.setAvailableAmount(accountInfo.getAvailableAmount() + amount);
        account.setInfoId(accountInfo);
        return transfersAccountRepository.save(account);
    }
}
