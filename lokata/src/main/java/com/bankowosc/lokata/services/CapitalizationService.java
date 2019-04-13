package com.bankowosc.lokata.services;

import com.bankowosc.lokata.models.Capitalization;
import com.bankowosc.lokata.models.CapitalizationType;
import com.bankowosc.lokata.repositories.CapitalizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CapitalizationService {
    
    @Autowired
    CapitalizationRepository capitalizationRepository;
    
    public Capitalization getCapitalizationById(Long id) {
        Optional<Capitalization> capitalization = capitalizationRepository.findById(id);
        if (!capitalization.isPresent()) {
            throw new NoSuchElementException();
        }
        return capitalization.get();
    }
    
    public Capitalization getCapitalizationByType(CapitalizationType type) {
        Optional<Capitalization> capitalization = capitalizationRepository.findByType(type);
        if (!capitalization.isPresent()) {
            throw new NoSuchElementException();
        }
        return capitalization.get();
    }
    
    public Capitalization addCapitalization(Capitalization capitalization) {
        return capitalizationRepository.save(capitalization);
    }
    
    public void deleteCapitalization(Capitalization capitalization) {
        capitalizationRepository.delete(capitalization);
    }
    
    
}
