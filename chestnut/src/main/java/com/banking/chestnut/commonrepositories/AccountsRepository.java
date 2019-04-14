package com.banking.chestnut.commonrepositories;

import com.banking.chestnut.models.Accounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepository extends JpaRepository<Accounts, Integer> {
}
