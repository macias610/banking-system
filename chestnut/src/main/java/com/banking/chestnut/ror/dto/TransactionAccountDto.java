package com.banking.chestnut.ror.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TransactionAccountDto implements Serializable {

    private TransactionSideDto sender;
    private TransactionSideDto receiver;
    private String title;
    private String type;
    private Long value;
}
