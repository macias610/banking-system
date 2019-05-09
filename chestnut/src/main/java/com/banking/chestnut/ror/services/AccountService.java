package com.banking.chestnut.ror.services;

import com.banking.chestnut.commonrepositories.UserRepository;
import com.banking.chestnut.helper.AccountNumberHelper;
import com.banking.chestnut.helper.NumericStringGenerator;
import com.banking.chestnut.models.Account;
import com.banking.chestnut.models.Client;
import com.banking.chestnut.models.Transaction;
import com.banking.chestnut.models.dto.AccountNumber;
import com.banking.chestnut.ror.dto.TransactionDto;
import com.banking.chestnut.ror.repositories.AccountInfoRepository;
import com.banking.chestnut.ror.repositories.AccountRepository;
import com.banking.chestnut.ror.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccountService implements IAccountService {

    private AccountRepository accountRepository;

    private UserRepository userRepository;

    private AccountInfoRepository accountInfoRepository;

    private TransactionRepository transactionRepository;

    private Environment env;

    private Integer cashierId;

    @Autowired
    public AccountService(AccountRepository accountRepository, Environment env, UserRepository userRepository,
                          AccountInfoRepository accountInfoRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.accountInfoRepository = accountInfoRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
        this.env = env;
        this.cashierId = Integer.parseInt(env.getProperty("app.cashier.user.id"));
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
    public Account editAccount(Account account) {
        this.accountRepository.save(account);
        return account;
    }

    @Override
    public Account deleteAccount(Account account) {
        account.setDeletedAt(new Date());
        account.setDeletedBy(userRepository.findById(cashierId).get());
        account.setIsActive(false);
        account.setIsBlocked(true);
        Long payout = account.getInfoId().getAvailableAmount();
        account.getInfoId().setAvailableAmount(new Long(0));
        this.accountRepository.save(account);
        Transaction transaction = new Transaction();
        transaction.setPayoutTransation(payout, userRepository.findById(cashierId).get(), account);
        this.transactionRepository.save(transaction);
        return account;
    }

    @Override
    public List<Transaction> getTransactionsByAccount(TransactionDto transactionDto, Integer accountId) {
        List<Transaction> transactions = findAllTransactionsByAccountId(accountId);
        List<Transaction> transactionsForReturn = new ArrayList<>();
        if (transactionDto != null){
            for (Transaction t: transactions) {
                boolean isValid = true;

                if (transactionDto.getType() != null){
                    if (t.getType().equalsIgnoreCase(transactionDto.getType())){

                    } else {
                        isValid = false;
                    }
                }

                if (transactionDto.getStartDate() != null){
                    if (t.getTransactionDate().getTime() >= transactionDto.getStartDate().getTime()){

                    } else {
                        isValid = false;
                    }
                }

                if (transactionDto.getEndDate() != null){
                    if (t.getTransactionDate().getTime() <= transactionDto.getEndDate().getTime()){

                    } else {
                        isValid = false;
                    }
                }

                if (isValid){
                    transactionsForReturn.add(t);
                }
            }
//            transactions = transactions.stream().filter(item -> (transactionDto.getType() != null && item.getType().equals(transactionDto.getType())
//                    || (transactionDto.getStartDate() != null && item.getTransactionDate().getTime() >= transactionDto.getStartDate().getTime())
//                    || (transactionDto.getEndDate() != null && item.getTransactionDate().getTime() <= transactionDto.getEndDate().getTime())))
//                    .collect(Collectors.toList());
        }
        return transactionsForReturn;
    }

    private List<Transaction> findAllTransactionsByAccountId(Integer accountId) {
        List<Transaction> outgoingTransactions = transactionRepository.findBySenderId_IdAndType(accountId, "outgoing");
        List<Transaction> incomingTransactions =  transactionRepository.findByReceiverId_IdAndType(accountId, "incoming");
        outgoingTransactions.addAll(incomingTransactions);
        return outgoingTransactions;
    }

    @Override
    public Optional<Account> getById(Integer id) {
        return this.accountRepository.findById(id);
    }

    @Override
    public List<Account> getAll() {
        return this.accountRepository.findAll();
    }

    @Override
    public List<Account> getClientAccounts(Integer clientId) {
        List<Account> accounts = this.accountRepository.findAll();
        accounts = accounts.stream().filter(item -> item.getClientId().getId().equals(clientId)).collect(Collectors.toList());
        return accounts;
    }
}
