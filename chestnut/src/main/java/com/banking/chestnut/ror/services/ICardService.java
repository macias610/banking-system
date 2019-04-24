package com.banking.chestnut.ror.services;

import com.banking.chestnut.models.Card;

import java.util.Optional;

public interface ICardService {
    Card saveCard(Card card);
    Card editCard(Card card);
    Optional<Card> getById(Integer id);
}
