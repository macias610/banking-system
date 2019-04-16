package com.banking.chestnut.ror.repositories;

import com.banking.chestnut.models.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contacts, Integer> {
}
