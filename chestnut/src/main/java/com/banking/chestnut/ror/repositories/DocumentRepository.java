package com.banking.chestnut.ror.repositories;

import com.banking.chestnut.models.Documents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Documents, Integer> {
}
