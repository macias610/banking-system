package com.banking.chestnut.deposit.services;

import com.banking.chestnut.deposit.repositories.CapitalizationRepository;
import com.banking.chestnut.models.Capitalization;
import com.banking.chestnut.models.CapitalizationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CapitalizationService {
    
    @Autowired
    CapitalizationRepository capitalizationRepository;
    
    public Capitalization getCapitalizationById(Integer id) {
        return capitalizationRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }
    
    public Capitalization getCapitalizationByType(CapitalizationType type) {
        return capitalizationRepository.findByType(type).orElseThrow(NoSuchElementException::new);
    }
    
    public Capitalization addCapitalization(Capitalization capitalization) {
        return capitalizationRepository.save(capitalization);
    }
    
    public void deleteCapitalization(Capitalization capitalization) {
        capitalizationRepository.delete(capitalization);
    }
    
    
}
