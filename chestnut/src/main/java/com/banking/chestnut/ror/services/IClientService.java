package com.banking.chestnut.ror.services;

import com.banking.chestnut.models.Clients;

import java.text.ParseException;
import java.util.Date;

public interface IClientService {
    Clients saveClient(Clients client);
    Date extractBirthdayFromPesel(Long pesel);
}
