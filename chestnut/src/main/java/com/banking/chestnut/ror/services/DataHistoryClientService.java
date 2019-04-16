package com.banking.chestnut.ror.services;

import com.banking.chestnut.commonrepositories.UserRepository;
import com.banking.chestnut.models.DataHistoryClient;
import com.banking.chestnut.ror.repositories.DataHistoryClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class DataHistoryClientService implements IDataHistoryClientService {

    private DataHistoryClientRepository dataHistoryClientRepository;

    private UserRepository userRepository;

    private Environment env;

    private Integer cashierId;

    @Autowired
    public DataHistoryClientService(DataHistoryClientRepository dataHistoryClientRepository,  UserRepository userRepository, Environment env) {
        this.env = env;
        this.cashierId = Integer.parseInt(env.getProperty("app.cashier.user.id"));
        this.dataHistoryClientRepository = dataHistoryClientRepository;
        this.userRepository = userRepository;
    }

    @Override
    public DataHistoryClient saveDataHistoryClient(DataHistoryClient dataHistoryClient) {
        dataHistoryClient.setCreatedAt(new Date());
        dataHistoryClient.setCreatedBy(userRepository.findById(cashierId).get());
        this.dataHistoryClientRepository.save(dataHistoryClient);
        return dataHistoryClient;
    }
}
