package com.banking.chestnut.ror.services;

import com.banking.chestnut.commonrepositories.BankRepository;
import com.banking.chestnut.commonrepositories.UserRepository;
import com.banking.chestnut.models.Client;
import com.banking.chestnut.ror.repositories.ClientRepository;
import com.banking.chestnut.ror.repositories.ClientStatusRepository;
import com.banking.chestnut.ror.repositories.ClientTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@Transactional
public class ClientService implements IClientService {

    private ClientRepository clientRepository;

    private UserRepository userRepository;

    private BankRepository bankRepository;

    private ClientStatusRepository clientStatusRepository;

    private ClientTypeRepository clientTypeRepository;

    private Environment env;

    private Integer cashierId;

    private Integer bankId;

    @Autowired
    public ClientService(ClientRepository clientRepository, UserRepository userRepository, BankRepository bankRepository,
                         ClientStatusRepository clientStatusRepository, ClientTypeRepository clientTypeRepository, Environment env) {
        this.env = env;
        this.cashierId = Integer.parseInt(env.getProperty("app.cashier.user.id"));
        this.bankId = Integer.parseInt(env.getProperty("app.bank.id"));
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.bankRepository = bankRepository;
        this.clientStatusRepository = clientStatusRepository;
        this.clientTypeRepository = clientTypeRepository;
    }

    @Override
    public Client saveClient(Client client) {
        client.setCreatedAt(new Date());
        client.setIsActive(true);
        client.setCreatedBy(userRepository.findById(cashierId).get());
        client.setBankId(bankRepository.findById(bankId).get());
        client.setClientStatusId(clientStatusRepository.findAll().stream().filter(item -> item.getName().equals("client active")).findFirst().get());
        client.setClientTypeId(clientTypeRepository.findAll().stream().filter(item -> item.getValue().equals("basic client")).findFirst().get());
        this.clientRepository.save(client);
        return client;
    }

    @Override
    public List<Client> getAll() {
        return this.clientRepository.findAllByDeletedAtNullAndDeletedByNull();
    }

    @Override
    public Optional<Client> getById(Integer id) {
        Optional<Client> client = this.clientRepository.findById(id);
        return client;
    }

    @Override
    public Date extractBirthdayFromPesel(Long pesel)  {
        String peselTxt = String.valueOf(pesel);
        byte PESEL[] = new byte[11];
        for (int i = 0; i < 11; i++){
            PESEL[i] = Byte.parseByte(peselTxt.substring(i, i+1));
        }
        Date birthDate = null;
        try {
            birthDate = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).
                    parse(getBirthYear(PESEL) + "" + getBirthMonth(PESEL) + "" + getBirthDay(PESEL));
        } catch (ParseException e) {
            return null;
        }
        return birthDate;
    }

    private int getBirthYear(byte[] PESEL) {
        int year;
        int month;
        year = 10 * PESEL[0];
        year += PESEL[1];
        month = 10 * PESEL[2];
        month += PESEL[3];
        if (month > 80 && month < 93) {
            year += 1800;
        }
        else if (month > 0 && month < 13) {
            year += 1900;
        }
        else if (month > 20 && month < 33) {
            year += 2000;
        }
        else if (month > 40 && month < 53) {
            year += 2100;
        }
        else if (month > 60 && month < 73) {
            year += 2200;
        }
        return year;
    }

    private  int getBirthMonth(byte[] PESEL) {
        int month;
        month = 10 * PESEL[2];
        month += PESEL[3];
        if (month > 80 && month < 93) {
            month -= 80;
        }
        else if (month > 20 && month < 33) {
            month -= 20;
        }
        else if (month > 40 && month < 53) {
            month -= 40;
        }
        else if (month > 60 && month < 73) {
            month -= 60;
        }
        return month;
    }

    public int getBirthDay(byte[] PESEL) {
        int day;
        day = 10 * PESEL[4];
        day += PESEL[5];
        return day;
    }
}
