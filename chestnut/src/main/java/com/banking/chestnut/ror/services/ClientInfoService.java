package com.banking.chestnut.ror.services;

import com.banking.chestnut.models.ClientInfo;
import com.banking.chestnut.ror.repositories.ClientInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ClientInfoService implements IClientInfoService {

    private ClientInfoRepository clientInfoRepository;

    @Autowired
    public ClientInfoService(ClientInfoRepository clientInfoRepository) {
        this.clientInfoRepository = clientInfoRepository;
    }


    @Override
    public ClientInfo save(ClientInfo clientInfo) {
        this.clientInfoRepository.save(clientInfo);
        return clientInfo;
    }
}
