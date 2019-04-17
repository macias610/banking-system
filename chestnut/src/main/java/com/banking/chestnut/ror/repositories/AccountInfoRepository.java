package com.banking.chestnut.ror.repositories;

import com.banking.chestnut.models.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountInfoRepository extends JpaRepository<AccountInfo, Integer> {
}
