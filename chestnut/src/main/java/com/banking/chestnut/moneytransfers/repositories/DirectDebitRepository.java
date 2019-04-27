package com.banking.chestnut.moneytransfers.repositories;

import com.banking.chestnut.models.DirectDebits;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DirectDebitRepository extends CrudRepository<DirectDebits, Integer>{
    List<DirectDebits> findByProviderId_Id(int providerId);
    DirectDebits findById(int id);
    List<DirectDebits> findByProviderId_IdAndIsEnabled(int providerId, boolean isEnabled);
}
