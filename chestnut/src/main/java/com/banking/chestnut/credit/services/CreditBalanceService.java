package com.banking.chestnut.credit.services;

import com.banking.chestnut.credit.repositories.CreditBalanceRepository;
import com.banking.chestnut.credit.repositories.CreditRepository;
import com.banking.chestnut.models.CreditBalance;
import com.banking.chestnut.models.Credits;
import com.banking.chestnut.models.DepositOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CreditBalanceService {

    @Autowired
    CreditBalanceRepository creditBalanceRepository;

    @Autowired
    CreditRepository creditRepository;

    public CreditBalance getCreditBalanceById(Integer id) {
        return creditBalanceRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Can't find balance for credit with id: " + id));
    }

    public void createCreditBalance(Credits credit){
        long loanPeriod = Integer.parseInt(credit.getCreditType().getLoan_period());
        float valueFromCredit = credit.getValue();
        float interestRateFromCreditType = credit.getCreditType().getInterest_rate();
        float interestFromCredit = valueFromCredit * interestRateFromCreditType/100;
        CreditBalance creditBalance = new CreditBalance();
        creditBalance.setPayments_left(loanPeriod);
        creditBalance.setDebt_asset(valueFromCredit);
        creditBalance.setDebt_interest(interestFromCredit);
        credit.setCreditBalance(creditBalance);
        creditBalanceRepository.save(creditBalance);
    }

}
