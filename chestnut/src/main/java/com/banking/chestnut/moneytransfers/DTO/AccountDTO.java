package com.banking.chestnut.moneytransfers.DTO;

import lombok.Data;

@Data
public class AccountDTO {
    private int id;
    private String accountNumber;
    private String type;
    private String clientName;
    private String clientSurname;
}
