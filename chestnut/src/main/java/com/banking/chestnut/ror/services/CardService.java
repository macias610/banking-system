package com.banking.chestnut.ror.services;

import com.banking.chestnut.commonrepositories.UserRepository;
import com.banking.chestnut.helper.BCryptUtility;
import com.banking.chestnut.helper.NumericStringGenerator;
import com.banking.chestnut.models.Card;
import com.banking.chestnut.ror.repositories.CardRepository;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CardService implements ICardService {

    private CardRepository cardRepository;

    private UserRepository userRepository;

    private Environment env;

    private Integer cashierId;

    @Autowired
    public CardService(CardRepository cardRepository, UserRepository userRepository, Environment environment) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.env = environment;
        this.cashierId = Integer.parseInt(env.getProperty("app.cashier.user.id"));
    }

    @Override
    public Card saveCard(Card card) {
        BCryptUtility bCryptUtility = new BCryptUtility(5);
        card.setCvv(bCryptUtility.hash(NumericStringGenerator.getAlphaNumericString(3)));
        card.setCreatedAt(new Date());
        card.setNumber(NumericStringGenerator.getAlphaNumericString(16));
        card.setPin(bCryptUtility.hash(NumericStringGenerator.getAlphaNumericString(4)));
        card.setType("Payment card");
        card.setStatus(true);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, 4);
        card.setValidityDate(calendar.getTime());
        card.setCreatedBy(userRepository.findById(cashierId).get());
        this.cardRepository.save(card);
        return card;
    }

    @Override
    public Card editCard(Card card) {
        this.cardRepository.save(card);
        return card;
    }

    @Override
    public List<Card> getByAccountId(Integer id) {
        return this.cardRepository.findAllByAccountId(id);
    }

    @Override
    public Optional<Card> getById(Integer id) {
        return this.cardRepository.findById(id);
    }
}
