package com.bankowosc.lokata.services;

import com.bankowosc.lokata.models.DepositType;
import com.bankowosc.lokata.repositories.DepositTypeRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DepositTypeService {
    
    @Autowired
    DepositTypeRespository depositTypeRespository;
    
    public DepositType getDepositTypeById(Long id) throws NoSuchElementException {
        return depositTypeRespository.findById(id).orElseThrow(NoSuchElementException::new);
    }
    
    public DepositType addDepositType(DepositType depositType) {
        return depositTypeRespository.save(depositType);
    }
    
    public void deleteDepositTypeById(Long id) throws NoSuchElementException {
        DepositType depositType = depositTypeRespository.findById(id).orElseThrow(NoSuchElementException::new);
        depositTypeRespository.delete(depositType);
    }
}
