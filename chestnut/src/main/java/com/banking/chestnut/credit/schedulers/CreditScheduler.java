package com.banking.chestnut.credit.schedulers;

import com.banking.chestnut.commonrepositories.UserRepository;
import com.banking.chestnut.credit.helpers.DateHelper;
import com.banking.chestnut.credit.services.CreditService;
import com.banking.chestnut.credit.services.PaymentScheduleService;
import com.banking.chestnut.models.*;
import com.banking.chestnut.moneytransfers.DTO.TransactionDTO;
import com.banking.chestnut.ror.repositories.TransactionRepository;
import com.banking.chestnut.ror.services.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.banking.chestnut.deposit.helpers.DateHelper.currentDate;


@Configuration
@EnableScheduling
public class CreditScheduler {

    @Autowired
    CreditService creditService;

    @Autowired
    PaymentScheduleService paymentScheduleService;

    @Autowired
    TransactionService transactionService;

    @Value("${app.system.user.id}")
    Integer systemUserId;

    @Value("${app.cashier.user.id}")
    Integer cashierId;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TransactionRepository transactionRepository;


    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Transactional
    @Scheduled(cron = "*/10 * * * * ?")
//    @Scheduled(initialDelay = 5000,fixedDelay = 5000) //once per 5 sec
//    @Scheduled(cron = "0 0 10 1 * ?") //on the first day of the month at 10 am
    public void processCreditPayments(){

        //transaction
        //id/created_at=currentdate/is_foreign=0/is_transfer_clients_accounts=0/is_viaBank=0/title(paymanet+date)type-outgoing/receiver_id=null/sender_id=accountid

        log.info("Credit executed at {}", dateFormat.format(new Date()));
        List<PaymentSchedule> activePaymentSchedules = paymentScheduleService.getAllActivePaymentSchedule();
        List<PaymentSchedule> endedPaymentSchedules = findEndedPaymentSchedule(activePaymentSchedules);
        endedPaymentSchedules.stream().forEach(endedPaymentSchedule -> {
        Credits credits = endedPaymentSchedule.getCredit();
        if(credits.getIsActive()){
            Float valueFromDebtAsset = endedPaymentSchedule.getCredit().getCreditBalance().getDebt_asset();
            Float valueFromInterestAsset = endedPaymentSchedule.getCredit().getCreditBalance().getDebt_interest();
            credits.getCreditBalance().setDebt_asset(valueFromDebtAsset - endedPaymentSchedule.getPayment_assets());
            credits.getCreditBalance().setDebt_interest(valueFromInterestAsset - endedPaymentSchedule.getPayment_interest());
            Long paymentsLeftFromCreditBalance = credits.getCreditBalance().getPayments_left();
            credits.getCreditBalance().setPayments_left(paymentsLeftFromCreditBalance - 1);
            AccountInfo accountInfo = credits.getAccount().getInfoId();
            accountInfo.setAvailableAmount(accountInfo.getAvailableAmount() - endedPaymentSchedule.getPayment_assets().longValue() - endedPaymentSchedule.getPayment_interest().longValue());
            endedPaymentSchedule.setActive(false);
            if(endedPaymentSchedule.getCredit().getCreditBalance().getPayments_left() < 1) {
                Credits credit = endedPaymentSchedule.getCredit();
                credit.setIsActive(false);
                }

                //id 	created_at 	is_foreign 	is_transfer_client_acconuts 	is_via_bank 	title 	transaction_date 	type 	value 	created_by 	receiver_id 	sender_id
                Transaction transaction = new Transaction();
                transaction.setCreatedAt(DateHelper.currentDate());
                transaction.setIsForeign(false);
                transaction.setIsTransferClientAcconuts(false);
                transaction.setIsViaBank(false);
                transaction.setTitle("Payment from "+endedPaymentSchedule.getPayment_date());
                transaction.setTransactionDate(DateHelper.currentDate());
                transaction.setType("incoming");
                transaction.setValue(endedPaymentSchedule.getPayment_assets().longValue()+endedPaymentSchedule.getPayment_interest().longValue());
                transaction.setCreatedBy(userRepository.findById(cashierId).get());
                transaction.setSenderId(endedPaymentSchedule.getCredit().getAccount());
                transactionRepository.save(transaction);
        }


        });

    }

    private List<PaymentSchedule> findEndedPaymentSchedule(List<PaymentSchedule> activePaymentSchedule) {
        return activePaymentSchedule.stream()
                .filter(d -> currentDate().compareTo(d.getPayment_date()) >= 0).collect(Collectors.toList());
    }

}
