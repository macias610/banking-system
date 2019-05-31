package com.banking.chestnut.credit.services;

import com.banking.chestnut.commonrepositories.UserRepository;
import com.banking.chestnut.credit.dto.CreditTypeDto;
import com.banking.chestnut.credit.helpers.DateHelper;
import com.banking.chestnut.credit.repositories.CreditTypeRepository;
import com.banking.chestnut.models.CreditType;
import com.banking.chestnut.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CreditTypeService {

    @Value("${app.cashier.user.id}")
    Integer cashierId;

    @Autowired
    UserRepository userRepository;

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
    public void oldDeleteCreditTypeById(Integer id) throws NoSuchElementException {
        CreditType creditType = creditTypeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Cannot find CreditType with id: " + id));
        creditTypeRepository.delete(creditType);
    }

    @Transactional
    public CreditTypeDto deleteCreditTypeById(Integer id) throws NoSuchElementException {
        CreditType creditType = creditTypeRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cannot find CreditType with id: " + id));
        creditType.setDeletedAt(DateHelper.currentTimestamp());
        User user = userRepository.findById(cashierId).orElseThrow(() -> new NoSuchElementException("Cannot find User with id: " + cashierId));
        creditType.setDeletedBy(user);
        return new CreditTypeDto(creditType);
    }

    public Set<CreditTypeDto> getAllActivecreditTypes() {
        List<CreditType> creditTypes = creditTypeRepository.findAllByDeletedAt(null).orElseThrow(() -> new NoSuchElementException("Cannot find any credit types"));
        return creditTypes.stream().map(CreditTypeDto::new).collect(Collectors.toSet());
    }
}
