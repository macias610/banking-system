package com.banking.chestnut.credit.services;

import com.banking.chestnut.commonrepositories.UserRepository;
import com.banking.chestnut.credit.dto.CreditBalanceDto;
import com.banking.chestnut.credit.helpers.DateHelper;
import com.banking.chestnut.credit.repositories.CreditBalanceRepository;
import com.banking.chestnut.credit.repositories.CreditRepository;
import com.banking.chestnut.models.*;
import com.banking.chestnut.ror.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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

    @Value("${app.cashier.user.id}")
    Integer cashierId;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    PaymentScheduleService paymentScheduleService;

    @Autowired
    CreditService creditService;

    public CreditBalanceDto getCreditBalanceById(Integer id) {
        CreditBalance creditBalance = creditBalanceRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Can't find balance for credit with id: " + id));
        return new CreditBalanceDto(creditBalance);
    }

    public Set<CreditBalanceDto> getAllCreditBalances() {
        List<CreditBalance> creditBalances = Optional.of((ArrayList<CreditBalance>) creditBalanceRepository.findAll()).orElseThrow(() -> new NoSuchElementException("Cannot find any credit balances"));
        return creditBalances.stream().map(CreditBalanceDto::new).collect(Collectors.toSet());
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

    //zerwanie umowy
    @Transactional
    public void closeCreditWithId(Integer id){
        Credits creditToClose = creditService.getCreditById(id);
        //CreditBalance creditBalance = getCreditBalanceById(creditToClose.getCreditBalance().getId());
        List<PaymentSchedule> activePaymentSchedules = paymentScheduleService.getAllActivePaymentSchedule();
        activePaymentSchedules.stream().forEach(ps -> {
            ps.setPayment_interest(0f);
            ps.setActive(false);
        });
        //umorzenie odsetek
        creditToClose.getCreditBalance().setDebt_interest(0f);
        Float amountToReturn = creditToClose.getCreditBalance().getDebt_asset();
        creditToClose.getCreditBalance().setDebt_asset(0f);
        creditToClose.getCreditBalance().setPayments_left(0l);
        AccountInfo accountInfo = creditToClose.getAccount().getInfoId();
        accountInfo.setAvailableAmount(accountInfo.getAvailableAmount() - amountToReturn.longValue());

        Transaction transaction = new Transaction();
        transaction.setCreatedAt(DateHelper.currentDate());
        transaction.setIsForeign(false);
        transaction.setIsTransferClientAcconuts(false);
        transaction.setIsViaBank(false);
        transaction.setTitle("Payment from "+ DateHelper.currentDate() + " - credit cancellation.");
        transaction.setTransactionDate(DateHelper.currentDate());
        transaction.setType("incoming");
        transaction.setValue(amountToReturn.longValue());
        transaction.setCreatedBy(userRepository.findById(cashierId).get());
        transaction.setSenderId(creditToClose.getAccount());
        transactionRepository.save(transaction);

    }

    //umorzenie kredytu
    @Transactional
    public void remitCreditWithId(Integer id){
        Credits creditToRemit = creditService.getCreditById(id);
        //CreditBalance creditBalance = getCreditBalanceById(creditToClose.getCreditBalance().getId());
        List<PaymentSchedule> activePaymentSchedules = paymentScheduleService.getAllActivePaymentSchedule();
        activePaymentSchedules.stream().forEach(ps -> {
            ps.setPayment_interest(0f);
            ps.setPayment_assets(0f);
            ps.setActive(false);
        });
        //umorzenie odsetek i dlugu
        creditToRemit.getCreditBalance().setDebt_interest(0f);
        //Float amountToReturn = creditToRemit.getCreditBalance().getDebt_asset();
        creditToRemit.getCreditBalance().setDebt_asset(0f);
        creditToRemit.getCreditBalance().setPayments_left(0l);

    }

}
