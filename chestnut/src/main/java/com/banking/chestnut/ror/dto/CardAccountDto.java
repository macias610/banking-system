package com.banking.chestnut.ror.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CardAccountDto implements Serializable {

    private Integer id;
    private String number;
    private Boolean status;

    public CardAccountDto() {
    }
}
