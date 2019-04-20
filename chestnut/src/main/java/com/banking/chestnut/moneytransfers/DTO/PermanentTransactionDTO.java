package com.banking.chestnut.moneytransfers.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class PermanentTransactionDTO extends TransactionDTO {
    private Date dateFrom;
    private Date dateTo;
    private int interval;
    private Date nextDate;
}
