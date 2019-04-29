package com.banking.chestnut.deposit.services;

import com.banking.chestnut.commonrepositories.UserRepository;
import com.banking.chestnut.deposit.dto.DepositTypeDto;
import com.banking.chestnut.deposit.helpers.DateHelper;
import com.banking.chestnut.deposit.repositories.CapitalizationRepository;
import com.banking.chestnut.deposit.repositories.DepositTypeRepository;
import com.banking.chestnut.models.DepositCapitalizations;
import com.banking.chestnut.models.DepositTypes;
import com.banking.chestnut.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepositTypeService {
    
    @Value("${app.cashier.user.id}")
    Integer cashierId;
    
    @Autowired
    UserRepository userRepository;
    
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
        DepositTypes depositTypes = depositTypeRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Cannot find DepositType with id: " + id));
        depositTypes.setDeletedAt(DateHelper.currentTimestamp());
        User user = userRepository.findById(cashierId).orElseThrow(() -> new NoSuchElementException("Cannot find User with id: " + cashierId));
        depositTypes.setDeletedBy(user);
    }
    
    public Set<DepositTypeDto> getAllDepositTypes(){
        List<DepositTypes> depositTypes = Optional.of((ArrayList<DepositTypes>) depositTypeRepository.findAll()).orElseThrow(NoSuchElementException::new);
        return depositTypes.stream().map(DepositTypeDto::new).collect(Collectors.toSet());
    }
}
