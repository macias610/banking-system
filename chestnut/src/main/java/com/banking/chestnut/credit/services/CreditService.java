package com.banking.chestnut.credit.services;

import com.banking.chestnut.credit.dto.CreditDto;
import com.banking.chestnut.credit.repositories.CreditRepository;
import com.banking.chestnut.models.Accounts;
import com.banking.chestnut.models.Credits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class CreditService {

    @Autowired
    CreditRepository creditRepository;

    public CreditDto getCreditById(Integer id){
        Credits credit = creditRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Credit not found"));
        return new CreditDto(credit);
    }

}
