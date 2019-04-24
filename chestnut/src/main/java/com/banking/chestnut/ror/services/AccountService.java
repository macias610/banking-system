package com.banking.chestnut.ror.services;

import com.banking.chestnut.helper.AccountNumberHelper;
import com.banking.chestnut.helper.NumericStringGenerator;
import com.banking.chestnut.models.Account;
import com.banking.chestnut.models.AccountType;
import com.banking.chestnut.models.Client;
import com.banking.chestnut.models.dto.AccountNumber;
import com.banking.chestnut.ror.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountService implements IAccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    @Override
    public Account saveAccount(Account account, Client client) {
        String accountNumber;
        List<Account> accounts = this.accountRepository.findAll();
        List<String> clientAccountNumers = accounts.stream().map(item -> item.getNumberClientAccount()).collect(Collectors.toList());
        AccountNumber generatedAccountNumber= null;
        do{
            accountNumber = NumericStringGenerator.getAlphaNumericString(16);
            generatedAccountNumber = AccountNumberHelper.generateAccountNumber(client, accountNumber);
        }while (clientAccountNumers.contains(accountNumber) || !AccountNumberHelper.checkIban(generatedAccountNumber.getIban()));


        account.setIban(generatedAccountNumber.getIban());
        account.setNumberBankingAccount(generatedAccountNumber.getNumber());
        account.setNumberClientAccount(accountNumber);
        account.setClientId(client);
        account.setIsActive(true);
        account.setIsBlocked(false);
        this.accountRepository.save(account);
        return account;
    }

    @Override
    public Optional<Account> getById(Integer id) {
        return this.accountRepository.findById(id);
    }

    @Override
    public List<Account> getAll() {
        return this.accountRepository.findAll();
    }
}
