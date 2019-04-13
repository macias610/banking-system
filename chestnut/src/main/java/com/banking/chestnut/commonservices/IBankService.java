package com.banking.chestnut.commonservices;

import com.banking.chestnut.models.Banks;

import java.util.Optional;

public interface IBankService {
    Optional<Banks> getBankInfo();
}
