package com.banking.chestnut.credit.services;

import com.banking.chestnut.credit.repositories.CreditTypeRepository;
import com.banking.chestnut.models.CreditType;
import com.banking.chestnut.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<CreditType> getAllCreditTypes(){
        List<CreditType> creditType = Optional.of((ArrayList<CreditType>) creditTypeRepository.findAll()).orElseThrow(() -> new NoSuchElementException("Cannot find any credit type"));
        return creditType;
    }

    @Transactional
    public void deleteCreditTypeById(Integer id) throws NoSuchElementException {
        CreditType creditType = creditTypeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Cannot find CreditType with id: " + id));
        creditTypeRepository.delete(creditType);
    }

}
