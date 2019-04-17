package com.banking.chestnut.ror.services;

import com.banking.chestnut.models.AccountInfo;
import com.banking.chestnut.ror.repositories.AccountInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountInfoService implements IAccountInfoService {

    private AccountInfoRepository accountInfoRepository;

    @Autowired
    public AccountInfoService(AccountInfoRepository accountInfoRepository) {
        this.accountInfoRepository = accountInfoRepository;
    }

    @Override
    public AccountInfo saveAccountInfo(AccountInfo accountInfo) {
        this.accountInfoRepository.save(accountInfo);
        return accountInfo;
    }
}
