package com.banking.chestnut.deposit.services;

import com.banking.chestnut.deposit.repositories.DepositTypeRepository;
import com.banking.chestnut.models.DepositTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class DepositTypeService {
    
    @Autowired
    DepositTypeRepository depositTypeRepository;
    
    public DepositTypes getDepositTypeById(Integer id) throws NoSuchElementException {
        return depositTypeRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
    
    public DepositTypes addDepositType(DepositTypes depositTypes) {
        return depositTypeRepository.save(depositTypes);
    }
    
    @Transactional
    public void deleteDepositTypeById(Integer id) throws NoSuchElementException {
        DepositTypes depositTypes = depositTypeRepository.findById(id).orElseThrow(NoSuchElementException::new);
        depositTypeRepository.delete(depositTypes);
    }
}
