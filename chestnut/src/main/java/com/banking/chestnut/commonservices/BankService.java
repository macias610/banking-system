package com.banking.chestnut.commonservices;

import com.banking.chestnut.commonrepositories.BankRepository;
import com.banking.chestnut.models.Banks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BankService implements IBankService {

    private BankRepository bankRepository;

    @Autowired
    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }


    @Override
    public Optional<Banks> getBankInfo() {
        List<Banks> banksList = this.bankRepository.findAll();
        if(banksList.isEmpty())
            return Optional.ofNullable(null);
        else
            return Optional.ofNullable(banksList.get(0));
    }
}
