package com.banking.chestnut.depositServices;

import com.banking.chestnut.depositRepositories.DepositTypeRepository;
import com.banking.chestnut.models.deposit.DepositType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class DepositTypeService {
    
    @Autowired
    DepositTypeRepository depositTypeRepository;
    
    public DepositType getDepositTypeById(Long id) throws NoSuchElementException {
        return depositTypeRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
    
    public DepositType addDepositType(DepositType depositType) {
        return depositTypeRepository.save(depositType);
    }
    
    public void deleteDepositTypeById(Long id) throws NoSuchElementException {
        DepositType depositType = depositTypeRepository.findById(id).orElseThrow(NoSuchElementException::new);
        depositTypeRepository.delete(depositType);
    }
}
