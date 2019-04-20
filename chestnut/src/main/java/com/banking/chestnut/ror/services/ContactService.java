package com.banking.chestnut.ror.services;

import com.banking.chestnut.commonrepositories.UserRepository;
import com.banking.chestnut.models.Contacts;
import com.banking.chestnut.ror.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class ContactService implements IContactService {

    private ContactRepository contactRepository;

    private UserRepository userRepository;

    private Environment env;

    private Integer cashierId;

    @Autowired
    public ContactService(ContactRepository contactRepository, UserRepository userRepository, Environment env) {
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
        this.env = env;
        this.cashierId = Integer.parseInt(env.getProperty("app.cashier.user.id"));
    }

    @Override
    public Contacts saveContact(Contacts contact) {
        contact.setCreatedAt(new Date());
        contact.setCreatedBy(userRepository.findById(cashierId).get());
        this.contactRepository.save(contact);
        return contact;
    }

    @Override
    public void deleteContact(Contacts contact) {
        this.contactRepository.delete(contact);
    }
}
