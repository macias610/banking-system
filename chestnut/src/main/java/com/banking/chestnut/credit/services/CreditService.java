package com.banking.chestnut.credit.services;

import com.banking.chestnut.credit.dto.CreditDto;
import com.banking.chestnut.credit.repositories.CreditRepository;
import com.banking.chestnut.credit.repositories.CreditTypeRepository;
import com.banking.chestnut.models.Account;
import com.banking.chestnut.models.CreditBalance;
import com.banking.chestnut.models.CreditType;
import com.banking.chestnut.models.Credits;
import com.banking.chestnut.ror.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class CreditService {

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

    public CreditDto getCreditById(Integer id){
        Credits credit = creditRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Credit not found"));
        return new CreditDto(credit);
    }

}
