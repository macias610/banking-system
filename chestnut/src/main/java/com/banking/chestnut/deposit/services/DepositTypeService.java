package com.banking.chestnut.deposit.services;

import com.banking.chestnut.deposit.dto.DepositTypeDto;
import com.banking.chestnut.deposit.repositories.CapitalizationRepository;
import com.banking.chestnut.deposit.repositories.DepositTypeRepository;
import com.banking.chestnut.models.DepositCapitalizations;
import com.banking.chestnut.models.DepositTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class DepositTypeService {
    
    @Autowired
    DepositTypeRepository depositTypeRepository;
    
    @Autowired
    CapitalizationRepository capitalizationRepository;
    
    public DepositTypeDto getDepositTypeById(Integer id) throws NoSuchElementException {
        DepositTypes depositTypes = depositTypeRepository.findById(id).orElseThrow(NoSuchElementException::new);
        return new DepositTypeDto(depositTypes);
    }
    
    @Transactional
    public DepositTypeDto addDepositType(DepositTypeDto depositTypeDto) {
        DepositCapitalizations capitalization = capitalizationRepository.findByType(depositTypeDto.getCapitalizationType()).orElseThrow(NoSuchElementException::new);
        DepositTypes addedDepositType = depositTypeRepository.save(new DepositTypes(depositTypeDto, capitalization));
        depositTypeDto.setId(addedDepositType.getId());
        return depositTypeDto;
    }
    
    @Transactional
    public void deleteDepositTypeById(Integer id) throws NoSuchElementException {
        DepositTypes depositTypes = depositTypeRepository.findById(id).orElseThrow(NoSuchElementException::new);
        depositTypeRepository.delete(depositTypes);
    }
}
