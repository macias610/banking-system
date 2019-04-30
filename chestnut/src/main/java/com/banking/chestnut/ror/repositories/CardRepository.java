package com.banking.chestnut.ror.repositories;

import com.banking.chestnut.models.Account;
import com.banking.chestnut.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Integer> {
    List<Card> findAllByAccountId(Account account);
}