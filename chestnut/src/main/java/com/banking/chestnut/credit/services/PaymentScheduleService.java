package com.banking.chestnut.credit.services;

import com.banking.chestnut.credit.repositories.PaymentScheduleRepository;
import com.banking.chestnut.models.PaymentSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class PaymentScheduleService {

    @Autowired //odpowiada za wstrzykniecia do repozytorium
    PaymentScheduleRepository paymentScheduleRepository;


    public PaymentSchedule getById(Long id){
       return paymentScheduleRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public PaymentSchedule addPaymentSchedule(PaymentSchedule paymentSchedule){
        return paymentScheduleRepository.save(paymentSchedule);
    }

}
