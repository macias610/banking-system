package com.banking.chestnut.moneytransfers.services;

import com.banking.chestnut.commonrepositories.UserRepository;
import com.banking.chestnut.models.Transactions;
import com.banking.chestnut.moneytransfers.DTO.TransactionDTO;
import com.banking.chestnut.moneytransfers.repositories.TransfersAccountRepository;
import com.banking.chestnut.moneytransfers.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransfersAccountRepository transfersAccountRepository;
    private final UserRepository userRepository;

    @Value("${app.cashier.user.id}")
    private int cashierId;
    @Value("${app.system.user.id}")
    private int systemId;

    public TransactionDTO findById(int id) {
        return prepareModel(transactionRepository.findById(id));
    }

    public List<TransactionDTO> findAllByReceiverIdOrSenderId(int accountId){
        List<Transactions> transactions = transactionRepository.findBySenderId_IdOrReceiverId_Id(accountId, accountId);
        List<TransactionDTO> transactionsDTO = new ArrayList<>();
        for (Transactions t: transactions) {
            transactionsDTO.add(prepareModel(t));
        }
        return transactionsDTO;
    }

    @Transactional
    public Transactions addTransaction(TransactionDTO transactionDTO, String type) {
        Transactions transaction = new Transactions();
        transaction.setTitle(transactionDTO.getTitle());
        transaction.setValue(transactionDTO.getValue());
        transaction.setSenderId(transfersAccountRepository.findById(transactionDTO.getSenderId()));
        transaction.setReceiverId(transfersAccountRepository.findById(transactionDTO.getReceiverId()));
        transaction.setTransactionDate(transactionDTO.getTransactionDate());
        transaction.setCreatedAt(new Date());
        transaction.setCreatedBy(userRepository.findById(systemId));
        transaction.setType(type);
        return transactionRepository.save(transaction);
    }

    private TransactionDTO prepareModel(Transactions transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setReceiverAccNumber(transaction.getReceiverId().getNumberClientAccount());
        dto.setSenderAccNumber(transaction.getSenderId().getNumberClientAccount());
        dto.setTransactionDate(transaction.getTransactionDate());
        dto.setValue(transaction.getValue());
        dto.setTitle(transaction.getTitle());
        dto.setSenderId(transaction.getSenderId().getId());
        dto.setReceiverId(transaction.getReceiverId().getId());
        return dto;
    }
}
