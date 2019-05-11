package com.banking.chestnut.ror.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TransactionSideDto implements Serializable {

    private String numberClientAccount;
    private String numberBankingAccount;
    private String currency;

}
