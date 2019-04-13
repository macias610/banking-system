package com.banking.chestnut.commonrepositories;

import com.banking.chestnut.models.Banks;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<Banks, Integer> {
}
