package com.banking.chestnut.ror.services;

import com.banking.chestnut.models.Client;

import java.util.Date;

public interface IClientService {
    Client saveClient(Client client);
    Date extractBirthdayFromPesel(Long pesel);
}
