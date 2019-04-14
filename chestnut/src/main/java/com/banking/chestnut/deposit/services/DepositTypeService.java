package com.banking.chestnut.deposit.services;

import com.banking.chestnut.deposit.repositories.DepositTypeRepository;
import com.banking.chestnut.models.DepositType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class DepositTypeService {
    
    @Autowired
    DepositTypeRepository depositTypeRepository;
    
    public DepositType getDepositTypeById(Integer id) throws NoSuchElementException {
        return depositTypeRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
    
    public DepositType addDepositType(DepositType depositType) {
        return depositTypeRepository.save(depositType);
    }
    
    @Transactional
    public void deleteDepositTypeById(Integer id) throws NoSuchElementException {
        DepositType depositType = depositTypeRepository.findById(id).orElseThrow(NoSuchElementException::new);
        depositTypeRepository.delete(depositType);
    }
}
