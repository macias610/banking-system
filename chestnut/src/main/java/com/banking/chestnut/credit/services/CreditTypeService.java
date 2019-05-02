package com.banking.chestnut.credit.services;

import com.banking.chestnut.credit.repositories.CreditTypeRepository;
import com.banking.chestnut.models.CreditType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CreditTypeService {

    @Autowired
    CreditTypeRepository creditTypeRepository;

    public CreditType getCreditTypeById(Integer id){
        return creditTypeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Credit type not found"));
    }

    public CreditType addCreditType(CreditType creditType) {
        return creditTypeRepository.save(creditType);
    }
}
