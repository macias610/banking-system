package com.banking.chestnut.ror.services;

import com.banking.chestnut.models.Account;
import com.banking.chestnut.models.Card;

import java.util.List;
import java.util.Optional;

public interface ICardService {
    Card saveCard(Card card);
    Card editCard(Card card);
    List<Card> getByAccountId(Account id);
    Optional<Card> getById(Integer id);
}