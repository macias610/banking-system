package com.banking.chestnut.moneytransfers.services;

import com.banking.chestnut.models.PermanentTransactions;
import com.banking.chestnut.moneytransfers.DTO.PermanentTransactionDTO;
import com.banking.chestnut.moneytransfers.repositories.TransfersAccountRepository;
import com.banking.chestnut.moneytransfers.repositories.PermanentTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PermanentTransactionService {

    private final PermanentTransactionRepository permanentTransactionRepository;
    private final TransfersAccountRepository transfersAccountRepository;

    public PermanentTransactionDTO findById(int id) {
        return prepareModel(permanentTransactionRepository.findById(id));
    }

    public List<PermanentTransactionDTO> findBySenderIdOrReceiverId(int accountId) {
        List<PermanentTransactions> transactions = permanentTransactionRepository.findByReceiverId_IdOrSenderId_Id(accountId, accountId);
        List<PermanentTransactionDTO> transactionsDTO = new ArrayList<>();
        for (PermanentTransactions t: transactions) {
            transactionsDTO.add(prepareModel(t));
        }
        return transactionsDTO;
    }

    @Transactional
    public PermanentTransactions addPermanentTransaction(PermanentTransactionDTO dto) {
        PermanentTransactions permanentTransaction = new PermanentTransactions();
        permanentTransaction.setTitle(dto.getTitle());
        permanentTransaction.setValue(dto.getValue());
        permanentTransaction.setSenderId(transfersAccountRepository.findById(dto.getSenderId()));
        permanentTransaction.setReceiverId(transfersAccountRepository.findById(dto.getReceiverId()));
        permanentTransaction.setDateFrom(dto.getDateFrom());
        permanentTransaction.setDateTo(dto.getDateTo());
        permanentTransaction.setIntervalTransaction(dto.getInterval());
        permanentTransaction.setNextDate(calculateNextDate(dto.getDateFrom(), dto.getInterval()));
        return permanentTransactionRepository.save(permanentTransaction);
    }

    public void cancelPermanentTransaction(int id) {
        PermanentTransactions permanentTransaction = permanentTransactionRepository.findById(id);
        permanentTransaction.setNextDate(new Date());
        permanentTransaction.setDateTo(new Date());
        permanentTransactionRepository.save(permanentTransaction);
    }

    private PermanentTransactionDTO prepareModel(PermanentTransactions permanentTransaction) {
        PermanentTransactionDTO dto = new PermanentTransactionDTO();
        dto.setId(permanentTransaction.getId());
        dto.setReceiverAccNumber(permanentTransaction.getReceiverId().getNumberClientAccount());
        dto.setSenderAccNumber(permanentTransaction.getSenderId().getNumberClientAccount());
        dto.setValue(permanentTransaction.getValue());
        dto.setTitle(permanentTransaction.getTitle());
        dto.setSenderId(permanentTransaction.getSenderId().getId());
        dto.setReceiverId(permanentTransaction.getReceiverId().getId());
        dto.setDateFrom(permanentTransaction.getDateFrom());
        dto.setDateTo(permanentTransaction.getDateTo());
        dto.setInterval(permanentTransaction.getIntervalTransaction());
        return dto;
    }

    private Date calculateNextDate(Date currentDate, int interval) {
        return Date.from(LocalDateTime.from(currentDate.toInstant()).plusDays(interval).atZone(ZoneId.systemDefault()).toInstant());
    }
}
