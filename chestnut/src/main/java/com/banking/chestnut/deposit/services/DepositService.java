package com.banking.chestnut.deposit.services;

import com.banking.chestnut.commonrepositories.UserRepository;
import com.banking.chestnut.deposit.dto.DepositDto;
import com.banking.chestnut.deposit.helpers.DateHelper;
import com.banking.chestnut.deposit.repositories.DepositRepository;
import com.banking.chestnut.deposit.repositories.DepositTypeRepository;
import com.banking.chestnut.deposit.repositories.OperationRepository;
import com.banking.chestnut.models.*;
import com.banking.chestnut.ror.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import static com.banking.chestnut.deposit.helpers.DateHelper.currentDate;
import static com.banking.chestnut.deposit.helpers.OperationFactory.createOperation;

@Service
public class DepositService {
    
    @Value("${app.cashier.user.id}")
    Integer cashierId;
    
    @Autowired
    DepositRepository depositRepository;
    
    @Autowired
    OperationRepository operationRepository;
    
    @Autowired
    AccountRepository accountsRepository;
    
    @Autowired
    DepositTypeRepository depositTypeRepository;
    
    @Autowired
    UserRepository userRepository;
    
    public Deposits getDepositById(Integer id) {
        return depositRepository.findById(id).
                      orElseThrow(() -> new NoSuchElementException("Deposit not found"));
    }
    
    public DepositDto getDepositDtoById(Integer id) {
        Deposits deposit = getDepositById(id);
        return new DepositDto(deposit);
    }
    
    public Set<DepositDto> getDepositsByAccountId(Integer id) {
        Set<Deposits> deposits = depositRepository.findAllByAccountId(id).orElseThrow(NoSuchElementException::new);
        return deposits.stream().map(d -> new DepositDto(d)).collect(Collectors.toSet());
    }
    
    public Set<Deposits> getAllActiveDeposits() {
        Set<Deposits> activeDeposits = depositRepository.findAllByIsActive(true).orElse(Collections.emptySet());
        return activeDeposits;
    }
    
    private void notifyOperationInDb(Deposits deposit, OperationType operationType) {
        DepositOperations closeDepositOperations = createOperation(operationType, deposit);
        operationRepository.save(closeDepositOperations);
    }
    
    @Transactional
    public DepositDto closeDepositWithId(Integer id) throws NoSuchElementException {
        Deposits depositsToClose = getDepositById(id);
        notifyOperationInDb(depositsToClose, OperationType.CLOSING);
        bringBackMoneyFromDepositToAccount(depositsToClose);
        depositsToClose.setEndDate(currentDate());
        depositsToClose.setIsActive(false);
        depositsToClose.setDeletedAt(DateHelper.currentTimestamp());
        User user = userRepository.findById(cashierId).
                      orElseThrow(() -> new NoSuchElementException("Cannot find User with id: " + cashierId));
        depositsToClose.setDeletedBy(user);
        return new DepositDto(depositsToClose);
    }
    
    private void bringBackMoneyFromDepositToAccount(Deposits depositsToClose) {
        AccountInfo accountInfo = depositsToClose.getAccount().getInfoId();
        Float depositAmount = depositsToClose.getAmount();
        accountInfo.setAvailableAmount(accountInfo.getAvailableAmount() + depositAmount.longValue());
    }
    
    @Transactional
    public DepositDto addDeposit(DepositDto depositDto) {
        Account account = accountsRepository.findById(depositDto.getAccountId()).
                      orElseThrow(() -> new NoSuchElementException("Cannot find Account with id: " + depositDto.getAccountId()));
        reduceAccountBalanceByDepositAmount(depositDto, account);
        DepositTypes depositType = depositTypeRepository.findById(depositDto.getDepositTypeId()).
                      orElseThrow(() -> new NoSuchElementException("Cannot find DepositType with id: " + depositDto.getDepositTypeId()));
        Deposits addedDeposits = depositRepository.save(new Deposits(depositDto, account, depositType));
        notifyOperationInDb(addedDeposits, OperationType.OPENING);
        depositDto.setId(addedDeposits.getId());
        return depositDto;
    }
    
    private void reduceAccountBalanceByDepositAmount(DepositDto depositDto, Account account) {
        AccountInfo accountInfo = account.getInfoId();
        if (isBalanceEnoughToCreateDeposit(accountInfo, depositDto)){
            Long accountBalanceAfterDepositCreation = (long) (accountInfo.getAvailableAmount() - depositDto.getAmount());
            accountInfo.setAvailableAmount(accountBalanceAfterDepositCreation);
        } else {
            throw new UnsupportedOperationException("Account balance is too low to make an operation");
        }
        account.setInfoId(accountInfo);
    }
    
    private Boolean isBalanceEnoughToCreateDeposit(AccountInfo accountInfo, DepositDto depositDto){
        if (accountInfo.getAvailableAmount() >= depositDto.getAmount()){
            return true;
        } else {
            return false;
        }
    }
}
