package com.banking.chestnut.ror.services;

import com.banking.chestnut.commonrepositories.BankRepository;
import com.banking.chestnut.commonrepositories.UserRepository;
import com.banking.chestnut.models.Client;
import com.banking.chestnut.models.ClientStatus;
import com.banking.chestnut.ror.repositories.ClientRepository;
import com.banking.chestnut.ror.repositories.ClientTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional
public class ClientService implements IClientService {

    private ClientRepository clientRepository;

    private UserRepository userRepository;

    private BankRepository bankRepository;

    private ClientTypeRepository clientTypeRepository;

    private Environment env;

    private Integer cashierId;

    private Integer bankId;

    @Autowired
    public ClientService(ClientRepository clientRepository, UserRepository userRepository, BankRepository bankRepository,
                         ClientTypeRepository clientTypeRepository, Environment env) {
        this.env = env;
        this.cashierId = Integer.parseInt(env.getProperty("app.cashier.user.id"));
        this.bankId = Integer.parseInt(env.getProperty("app.bank.id"));
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.bankRepository = bankRepository;
        this.clientTypeRepository = clientTypeRepository;
    }

    @Override
    public Client saveClient(Client client) {
        client.setCreatedAt(new Date());
        client.setIsActive(true);
        client.setCreatedBy(userRepository.findById(cashierId).get());
        client.setBankId(bankRepository.findById(bankId).get());
        client.setClientStatus(ClientStatus.statuses.get("active"));
        client.setClientTypeId(clientTypeRepository.findAll().stream().filter(item -> item.getValue().equals("individual client")).findFirst().get());
        this.clientRepository.save(client);
        return client;
    }

    @Override
    public Client deleteClient(Client client) {
        client.setDeletedAt(new Date());
        client.setDeletedBy(userRepository.findById(cashierId).get());
        client.setClientStatus(ClientStatus.statuses.get("nonactive"));
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
    public Optional<Client> getByPesel(Long pesel) {
        List<Client> clients = this.clientRepository.findAll();
        return clients.stream().filter(item -> item.getClientInfoId().getPesel().equals(pesel)).findFirst();
    }

    @Override
    public Date extractBirthdayFromPesel(Long pesel)  {
        String peselTxt = String.valueOf(pesel);
        byte PESEL[] = new byte[11];
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyyMMdd");
        for (int i = 0; i < 11; i++){
            PESEL[i] = Byte.parseByte(peselTxt.substring(i, i+1));
        }
        Date birthDate = null;
        try {
            Date dd = new GregorianCalendar(getBirthYear(PESEL), getBirthMonth(PESEL), getBirthDay(PESEL)).getTime();
            birthDate = formatter.parse(formatter.format(dd));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return birthDate;
    }

    @Override
    public boolean isValidPesel(Long pesel) {
        String peselTxt = String.valueOf(pesel);
        byte PESEL[] = new byte[11];
        for (int i = 0; i < 11; i++){
            PESEL[i] = Byte.parseByte(peselTxt.substring(i, i+1));
        }
        if (checkSum(PESEL) && checkMonth(PESEL) && checkDay(PESEL)) {
            return true;
        }
        else {
            return false;
        }
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

    private boolean checkSum(byte[] PESEL) {
        int sum = 1 * PESEL[0] +
                3 * PESEL[1] +
                7 * PESEL[2] +
                9 * PESEL[3] +
                1 * PESEL[4] +
                3 * PESEL[5] +
                7 * PESEL[6] +
                9 * PESEL[7] +
                1 * PESEL[8] +
                3 * PESEL[9];
        sum %= 10;
        sum = 10 - sum;
        sum %= 10;

        if (sum == PESEL[10]) {
            return true;
        }
        else {
            return false;
        }
    }

    private boolean checkMonth(byte[] PESEL) {
        int month = getBirthMonth(PESEL);
        int day = getBirthDay(PESEL);
        if (month > 0 && month < 13) {
            return true;
        }
        else {
            return false;
        }
    }

    private boolean checkDay(byte[] PESEL) {
        int year = getBirthYear(PESEL);
        int month = getBirthMonth(PESEL);
        int day = getBirthDay(PESEL);
        if ((day >0 && day < 32) &&
                (month == 1 || month == 3 || month == 5 ||
                        month == 7 || month == 8 || month == 10 ||
                        month == 12)) {
            return true;
        }
        else if ((day >0 && day < 31) &&
                (month == 4 || month == 6 || month == 9 ||
                        month == 11)) {
            return true;
        }
        else if ((day >0 && day < 30 && leapYear(year)) ||
                (day >0 && day < 29 && !leapYear(year))) {
            return true;
        }
        else {
            return false;
        }
    }

    private boolean leapYear(int year) {
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
            return true;
        else
            return false;
    }
}
