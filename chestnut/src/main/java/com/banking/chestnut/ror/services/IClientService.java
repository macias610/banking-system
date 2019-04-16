package com.banking.chestnut.ror.services;

import com.banking.chestnut.models.Client;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IClientService {
    Client saveClient(Client client);
    List<Client> getAll();
    Optional<Client> getById(Integer id);
    Optional<Client> getByPesel(Long pesel);
    Date extractBirthdayFromPesel(Long pesel);
    boolean isValidPesel(Long pesel);
}
