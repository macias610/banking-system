package com.banking.chestnut.moneytransfers.services;

import com.banking.chestnut.models.PermanentTransactions;
import com.banking.chestnut.moneytransfers.DTO.PermanentTransactionDTO;
import com.banking.chestnut.moneytransfers.repositories.TransfersAccountRepository;
import com.banking.chestnut.moneytransfers.repositories.PermanentTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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

    public List<PermanentTransactionDTO> findBySenderId(int accountId) {
        List<PermanentTransactions> transactions = permanentTransactionRepository.findBySenderId_Id(accountId);
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
        permanentTransaction.setSenderId(transfersAccountRepository.findByNumberBankingAccount(dto.getSenderAccNumber()));
        permanentTransaction.setReceiverId(transfersAccountRepository.findByNumberBankingAccount(dto.getReceiverAccNumber()));
        permanentTransaction.setDateFrom(Date.from(dto.getDateFrom().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        permanentTransaction.setDateTo(Date.from(dto.getDateTo().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        permanentTransaction.setIntervalTransaction(dto.getInterval());
        permanentTransaction.setEnabled(true);
        permanentTransaction.setNextDate(Date.from(dto.getDateFrom().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        return permanentTransactionRepository.save(permanentTransaction);
    }

    public void cancelPermanentTransaction(int id) {
        PermanentTransactions permanentTransaction = permanentTransactionRepository.findById(id);
        permanentTransaction.setEnabled(false);
        permanentTransaction.setNextDate(new Date());
        permanentTransactionRepository.save(permanentTransaction);
    }

    public List<PermanentTransactions> findByNextDateAndEnabled(Date nextDate) {
        return permanentTransactionRepository.findByNextDateAndIsEnabled(nextDate, true);
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
        dto.setDateFrom(permanentTransaction.getDateFrom().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        dto.setDateTo(permanentTransaction.getDateTo().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        dto.setInterval(permanentTransaction.getIntervalTransaction());
        dto.setEnabled(permanentTransaction.isEnabled());
        return dto;
    }

    public void updateNextDate(int id) {
        PermanentTransactions permanentTransaction = permanentTransactionRepository.findById(id);
        LocalDate nextDate = calculateNextDate(permanentTransaction.getNextDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), permanentTransaction.getIntervalTransaction());
        if (nextDate.isBefore(permanentTransaction.getDateTo().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) || nextDate.equals(permanentTransaction.getDateTo())) {
            permanentTransaction.setNextDate(Date.from(nextDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            permanentTransactionRepository.save(permanentTransaction);
        }
    }

    private LocalDate calculateNextDate(LocalDate currentDate, int interval) {
        return currentDate.plusDays(interval).atStartOfDay().toLocalDate();
    }
}
