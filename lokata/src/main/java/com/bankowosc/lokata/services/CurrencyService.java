package com.bankowosc.lokata.services;

import com.bankowosc.lokata.models.Currencies;
import com.bankowosc.lokata.repositories.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CurrencyService {
    
    @Autowired
    CurrencyRepository currencyRepository;
    
    public Currencies getCurrencyById(Long id) {
        Optional<Currencies> currency = currencyRepository.findById(id);
        if (!currency.isPresent()) {
            throw new NoSuchElementException();
        }
        return currency.get();
    }
    
    public Currencies getCurrencyByName(String name) {
        Optional<Currencies> currency = currencyRepository.findByName(name);
        if (!currency.isPresent()) {
            throw new NoSuchElementException();
        }
        return currency.get();
    }
    
    public Currencies addCurrency(Currencies currencies) {
        return currencyRepository.save(currencies);
    }
    
    public void deleteCurrency(Currencies currencies) {
        currencyRepository.delete(currencies);
    }
}
