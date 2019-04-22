package com.banking.chestnut.moneytransfers.DTO;

import lombok.Data;

@Data
public class DirectDebitDTO {
    private int id;
    private String providerAccNumber;
    private String clientAccNumber;
    private int providerId;
    private int clientId;
    private boolean isEnabled;
}
