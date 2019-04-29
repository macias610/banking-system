package com.banking.chestnut.deposit.services;

import com.banking.chestnut.deposit.repositories.CapitalizationRepository;
import com.banking.chestnut.models.CapitalizationType;
import com.banking.chestnut.models.DepositCapitalizations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class CapitalizationService {
    
    @Autowired
    CapitalizationRepository capitalizationRepository;
    
    public DepositCapitalizations getCapitalizationById(Integer id) {
        return capitalizationRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
    
    public DepositCapitalizations getCapitalizationByType(CapitalizationType type) {
        return capitalizationRepository.findByType(type).orElseThrow(NoSuchElementException::new);
    }
    
    public DepositCapitalizations addCapitalization(DepositCapitalizations depositCapitalizations) {
        return capitalizationRepository.save(depositCapitalizations);
    }
    
    public void deleteCapitalization(DepositCapitalizations depositCapitalizations) {
        capitalizationRepository.delete(depositCapitalizations);
    }
    
}
