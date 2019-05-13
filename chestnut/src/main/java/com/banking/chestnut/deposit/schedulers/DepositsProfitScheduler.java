package com.banking.chestnut.deposit.schedulers;


import com.banking.chestnut.commonservices.UserService;
import com.banking.chestnut.deposit.services.DepositService;
import com.banking.chestnut.models.AccountInfo;
import com.banking.chestnut.models.Deposits;
import com.banking.chestnut.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

import static com.banking.chestnut.deposit.helpers.DateHelper.*;
import static java.time.temporal.ChronoUnit.DAYS;

@Configuration
@EnableScheduling
public class DepositsProfitScheduler {
    
    @Autowired
    DepositService depositService;
    
    @Autowired
    UserService userService;
    
    @Value("${app.system.user.id}")
    Integer systemUserId;
    
    @Transactional
    @Scheduled(fixedRate = 5000)
//    @Scheduled(cron = "0 0 10 * * *") //everyday at 10 am
    public void processProfitsFromEndedDeposits() {
        
        Set<Deposits> activeDeposits = depositService.getAllActiveDeposits();
        
        Set<Deposits> endedDeposits = findEndedDeposits(activeDeposits);
        
        endedDeposits.stream().forEach(endedDeposit -> {
    
            Long depositPeriod;
            if(endedDeposit.getDepositType().getName() == "Test"){
                depositPeriod = 30l;
            } else {
                depositPeriod = calculateDepositPeriod(endedDeposit);
            }
            
            Integer capitalizationPeriod = endedDeposit.getDepositType().getCapitalization().getDaysPeriod();
            Double earningsFromDeposit = calculateEarningsFromDeposit(endedDeposit, depositPeriod, capitalizationPeriod);
            AccountInfo accountInfo = endedDeposit.getAccount().getInfoId();
            accountInfo.setAvailableAmount(accountInfo.getAvailableAmount() + earningsFromDeposit.longValue());
            endedDeposit.setDeletedBy(getSystemUser());
            endedDeposit.setDeletedAt(currentTimestamp());
            endedDeposit.setIsActive(false);
        });
        
    }
    
    private User getSystemUser() {
        return userService.findById(systemUserId).orElse(null);
    }
    
    private long calculateDepositPeriod(Deposits endedDeposit) {
        return DAYS.between(endedDeposit.getEndDate().toLocalDate(), endedDeposit.getStartDate().toLocalDate());
    }
    
    private double calculateEarningsFromDeposit(Deposits endedDeposit, Long depositPeriod, Integer capitalizationPeriod) {
        Float depositAmount = endedDeposit.getAmount();
        Float interestRate = endedDeposit.getDepositType().getInterestRate() / 100f * (capitalizationPeriod / daysInCurrentYear());
        Long numberOfCapitalizationInDepositPeriod = depositPeriod / capitalizationPeriod;
        return depositAmount * Math.pow((1 + interestRate), numberOfCapitalizationInDepositPeriod);
    }
    
    private Set<Deposits> findEndedDeposits(Set<Deposits> activeDeposits) {
        return activeDeposits.stream()
                      .filter(d -> currentDate().compareTo(d.getEndDate()) >= 0).collect(Collectors.toSet());
    }
    
}
