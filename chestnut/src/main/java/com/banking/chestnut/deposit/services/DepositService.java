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
    
    public DepositDto getDepositById(Integer id) {
        Deposits deposit = depositRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return new DepositDto(deposit);
    }
    
    @Transactional
    public DepositDto closeDepositWithId(Integer id) throws NoSuchElementException {
        Deposits depositsToClose = depositRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Cannot find Deposit with id: " + id));
        DepositOperations closeDepositOperations = createOperation(OperationType.CLOSING, depositsToClose);
        operationRepository.save(closeDepositOperations);
        AccountInfo accountInfo = depositsToClose.getAccount().getInfoId();
        Float depositAmount = depositsToClose.getAmount();
        accountInfo.setAvailableAmount(accountInfo.getAvailableAmount() + depositAmount.longValue());
        depositsToClose.setEndDate(currentDate());
        depositsToClose.setIsActive(false);
        depositsToClose.setDeletedAt(DateHelper.currentTimestamp());
        User user = userRepository.findById(cashierId).orElseThrow(() -> new NoSuchElementException("Cannot find User with id: " + cashierId));
        depositsToClose.setDeletedBy(user);
        return new DepositDto(depositsToClose);
    }
    
    public Set<DepositDto> getDepositsByAccountId(Integer id) {
        Set<Deposits> deposits = depositRepository.findAllByAccountId(id).orElseThrow(NoSuchElementException::new);
        return deposits.stream().map(d -> new DepositDto(d)).collect(Collectors.toSet());
    }
    
    @Transactional
    public DepositDto addDeposit(DepositDto depositDto) {
        Account account = accountsRepository.findById(depositDto.getAccountId()).orElseThrow(() -> new NoSuchElementException("Cannot find Account with id: " + depositDto.getAccountId()));
        DepositTypes depositType = depositTypeRepository.findById(depositDto.getDepositTypeId()).orElseThrow(() -> new NoSuchElementException("Cannot find DepositType with id: " + depositDto.getDepositTypeId()));
        Deposits addedDeposits = depositRepository.save(new Deposits(depositDto, account, depositType));
        DepositOperations addDepositOperations = createOperation(OperationType.OPENING, addedDeposits);
        operationRepository.save(addDepositOperations);
        depositDto.setId(addedDeposits.getId());
        return depositDto;
    }
}
