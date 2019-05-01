package com.banking.chestnut.moneytransfers.utils;


import com.banking.chestnut.models.PermanentTransactions;
import com.banking.chestnut.models.Transaction;
import com.banking.chestnut.moneytransfers.services.MoneyTransactionService;
import com.banking.chestnut.moneytransfers.services.PermanentTransactionService;
import com.banking.chestnut.moneytransfers.services.TransfersAccountService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionScheduler {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final PermanentTransactionService permanentTransactionService;
    private final MoneyTransactionService moneyTransactionService;
    private final TransfersAccountService transfersAccountService;

    @Scheduled(cron = "0 1 0 * * ?")
    public void executeTask() {
        Date currentDate = new Date();
        List<PermanentTransactions> permanentTransactions = permanentTransactionService.findByNextDate(currentDate);
        for (PermanentTransactions t: permanentTransactions) {
            Transaction outgoing = moneyTransactionService.addTransaction(t, "outgoing", currentDate);
            Transaction incoming = moneyTransactionService.addTransaction(t, "incoming", currentDate);
            transfersAccountService.updateAvailableAmount(outgoing.getSenderId().getId(), -outgoing.getValue());
            transfersAccountService.updateAvailableAmount(incoming.getReceiverId().getId(), incoming.getValue());
            permanentTransactionService.updateNextDate(t.getId());
        }
        log.info("Task executed at {}", dateFormat.format(new Date()));
    }
}
