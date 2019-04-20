package com.banking.chestnut.credit.services;

import com.banking.chestnut.credit.repositories.CreditRepository;
import com.banking.chestnut.models.Credits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class CreditService {

    @Autowired
    CreditRepository creditRepository;

    public Credits getById(Integer id){
        return creditRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Credits addCredit(Credits credits){
        return creditRepository.save(credits);
    }

}