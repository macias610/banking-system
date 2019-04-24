package com.banking.chestnut.ror.dto;

import java.io.Serializable;

public class CardAccountDto implements Serializable {

    private Integer id;
    private String number;
    private Boolean status;

    public CardAccountDto() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
