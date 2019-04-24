package com.banking.chestnut.ror.dto;

import java.io.Serializable;

public class CardDto implements Serializable {

    private Integer accountId;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
}
