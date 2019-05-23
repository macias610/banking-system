package com.banking.chestnut.credit.services;

import com.banking.chestnut.commonrepositories.UserRepository;
import com.banking.chestnut.credit.dto.CreditDto;
import com.banking.chestnut.credit.repositories.CreditRepository;
import com.banking.chestnut.credit.repositories.CreditTypeRepository;
import com.banking.chestnut.models.*;
import com.banking.chestnut.moneytransfers.DTO.TransactionDTO;
import com.banking.chestnut.ror.repositories.AccountRepository;
import com.banking.chestnut.ror.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Set;

import static com.banking.chestnut.credit.helpers.DateHelper.currentDate;
import static com.banking.chestnut.credit.helpers.DateHelper.addMonths;

@Service
public class CreditService {

    @Value("${app.cashier.user.id}")
    Integer cashierId;

    @Autowired
    CreditRepository creditRepository;

    @Autowired
    CreditTypeRepository creditTypeRepository;

    @Autowired
    PaymentScheduleService paymentScheduleService;

    @Autowired
    CreditBalanceService creditBalanceService;

    @Autowired
    AccountRepository accountsRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;

    public CreditDto getCreditById(Integer id){
        Credits credit = creditRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Credit not found"));
        return new CreditDto(credit);
    }

    @Transactional
    public void addCredit(CreditDto creditDto){
        Account account = accountsRepository.findById(creditDto.getAccountId()).orElseThrow(() -> new NoSuchElementException("Cannot find Account with id: " + creditDto.getAccountId()));
        CreditType creditType = creditTypeRepository.findById(creditDto.getCreditTypeId()).orElseThrow(() -> new NoSuchElementException("Cannot find Credit Type with id: " + creditDto.getCreditTypeId()));
        if (isCreditValueCorrectForCreditType(creditDto)){
            Credits addedCredit = new Credits(creditDto, account, creditType);
            addedCredit.setIsActive(true);
            addedCredit.setCreated_by(userRepository.findById(cashierId).get());
            addedCredit.setCreated_at(currentDate());
            addedCredit.setExpiration_at(addMonths(addedCredit.getCreated_at() ,Long.parseLong(creditType.getLoan_period())));
            creditRepository.save(addedCredit);
            addCreditValueToAccountBallance(creditDto,account);
            paymentScheduleService.createPaymentSchedule(addedCredit);
            creditBalanceService.createCreditBalance(addedCredit);
            //id 	created_at 	is_foreign 	is_transfer_client_acconuts 	is_via_bank 	title 	transaction_date 	type 	value 	created_by 	receiver_id 	sender_id
            Transaction transaction = new Transaction();
            transaction.setCreatedAt(currentDate());
            transaction.setIsForeign(false);
            transaction.setIsTransferClientAcconuts(false);
            transaction.setIsViaBank(false);
            transaction.setTitle("Credit "+addedCredit.getCreated_at());
            transaction.setTransactionDate(currentDate());
            transaction.setType("outgoing");
            transaction.setValue(addedCredit.getValue());
            transaction.setCreatedBy(userRepository.findById(cashierId).get());
            transaction.setReceiverId(addedCredit.getAccount());
            transactionRepository.save(transaction);
        }
        else{
            throw new UnsupportedOperationException("Credit value is incorrect for the selected Credit Type");
        }
    }

    //    @Transactional
//    public Transaction addTransaction(TransactionDTO transactionDTO, String type) {
//        Transaction transaction = new Transaction();
//        transaction.setTitle(transactionDTO.getTitle());
//        if (type.equals("outgoing"))
//            transaction.setValue(-transactionDTO.getValue());
//        else
//            transaction.setValue(transactionDTO.getValue());
//        transaction.setSenderId(transfersAccountRepository.findByNumberBankingAccount(transactionDTO.getSenderAccNumber()));
//        transaction.setReceiverId(transfersAccountRepository.findByNumberBankingAccount(transactionDTO.getReceiverAccNumber()));
//        transaction.setTransactionDate(transactionDTO.getTransactionDate());
//        transaction.setCreatedAt(new Date());
//        transaction.setCreatedBy(userRepository.findById(systemId));
//        transaction.setType(type);
//        return moneyTransactionRepository.save(transaction);
//    }


    private void addCreditValueToAccountBallance(CreditDto creditDto, Account account){
        AccountInfo accountInfo = account.getInfoId();
        Long accountBalanceAfterCreatingCredit =  accountInfo.getAvailableAmount() + creditDto.getValue();
        accountInfo.setAvailableAmount(accountBalanceAfterCreatingCredit);
    }

    private Boolean isCreditValueCorrectForCreditType(CreditDto creditDto){
        CreditType creditType = creditTypeRepository.findById(creditDto.getCreditTypeId()).orElseThrow(() -> new NoSuchElementException("Cannot find Credit Type with id: " + creditDto.getCreditTypeId()));
        if(creditDto.getValue() >= creditType.getMin_value() && creditDto.getValue() <= creditType.getMax_value()){
            return true;
        }
        else{
            return false;
        }
    }

}
