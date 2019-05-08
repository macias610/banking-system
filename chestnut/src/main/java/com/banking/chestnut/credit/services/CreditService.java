package com.banking.chestnut.credit.services;

import com.banking.chestnut.commonrepositories.UserRepository;
import com.banking.chestnut.credit.dto.CreditDto;
import com.banking.chestnut.credit.repositories.CreditRepository;
import com.banking.chestnut.credit.repositories.CreditTypeRepository;
import com.banking.chestnut.models.*;
import com.banking.chestnut.ror.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static com.banking.chestnut.credit.helpers.DateHelper.currentDate;

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

    public CreditDto getCreditById(Integer id){
        Credits credit = creditRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Credit not found"));
        return new CreditDto(credit);
    }

    @Transactional
    public CreditDto addCredit(CreditDto creditDto){
        Account account = accountsRepository.findById(creditDto.getAccountId()).orElseThrow(() -> new NoSuchElementException("Cannot find Account with id: " + creditDto.getAccountId()));
        CreditType creditType = creditTypeRepository.findById(creditDto.getCreditTypeId()).orElseThrow(() -> new NoSuchElementException("Cannot find Credit Type with id: " + creditDto.getCreditTypeId()));
        if (isCreditValueCorrectForCreditType(creditDto)){
            Credits addedCredit = creditRepository.save(new Credits(creditDto, account, creditType));
            addedCredit.setIsActive(true);
            addedCredit.setCreated_by(userRepository.findById(cashierId).get());
            addedCredit.setCreated_at(currentDate());
            //TODO
            addedCredit.setExpiration_at(currentDate());
            creditRepository.save(addedCredit);
            addCreditValueToAccountBallance(creditDto,account);
            creditDto.setId(addedCredit.getId());
            return creditDto;
        }
        else{
            throw new UnsupportedOperationException("Credit value is incorrect for the selected Credit Type");
        }
    }


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
