package com.banking.chestnut.ror.repositories;

import com.banking.chestnut.models.DataHistoryClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataHistoryClientRepository extends JpaRepository<DataHistoryClient, Integer> {
}
