package com.banking.chestnut.ror.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TransactionAccountDto implements Serializable {

    private TransactionSideDto sender;
    private TransactionSideDto receiver;
    private Date transactionDate;
    private String title;
    private String type;
    private Long value;
}
