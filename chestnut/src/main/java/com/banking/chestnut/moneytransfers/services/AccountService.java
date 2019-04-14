package com.banking.chestnut.moneytransfers.services;

import com.banking.chestnut.models.AccountInfo;
import com.banking.chestnut.models.Accounts;
import com.banking.chestnut.moneytransfers.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountService {

    private final AccountRepository accountRepository;

    public Accounts findByClientId(int clientId) {
        return accountRepository.findByClientId(clientId);
    }

    @Transactional
    public Accounts updateAvailableAmount(int accountId, long amount) {
        Accounts account = accountRepository.findById(accountId);
        AccountInfo accountInfo = account.getInfoId();
        accountInfo.setAvailableAmount(accountInfo.getAvailableAmount() + amount);
        account.setInfoId(accountInfo);
        return accountRepository.save(account);
    }
}
