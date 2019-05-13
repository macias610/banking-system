package com.banking.chestnut.credit.services;

import com.banking.chestnut.credit.repositories.PaymentScheduleRepository;
import com.banking.chestnut.models.Credits;
import com.banking.chestnut.models.PaymentSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.NoSuchElementException;

import static com.banking.chestnut.credit.helpers.DateHelper.addMonths;

@Service
public class PaymentScheduleService {

    @Autowired
    PaymentScheduleRepository paymentScheduleRepository;


    public PaymentSchedule getPaymentScheduleById(Integer id){
       return paymentScheduleRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Payment Schedule not found"));
    }

    public void createPaymentSchedule(Credits credit){
        int loanPeriod = Integer.parseInt(credit.getCreditType().getLoan_period());
        Float monthlyAssets = credit.getValue().floatValue()/loanPeriod;
        Float monthlyInterest = (credit.getValue()*(credit.getCreditType().getInterest_rate()/100))/loanPeriod;
        Date paymentDate = credit.getCreated_at();
        for(int i=0;i<loanPeriod;i++){
            PaymentSchedule ps = new PaymentSchedule();
            paymentDate = addMonths(paymentDate, 1l);
            ps.setPayment_date(paymentDate);
            ps.setPayment_assets(monthlyAssets);
            ps.setPayment_interest(monthlyInterest);
            ps.setCredit(credit);
            paymentScheduleRepository.save(ps);
        }
    }

}
