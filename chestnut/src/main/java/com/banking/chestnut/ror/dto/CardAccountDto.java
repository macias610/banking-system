package com.banking.chestnut.ror.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CardAccountDto implements Serializable {

    private Integer id;
    private String number;
    private String type;
    private Date validityDate;
    private Boolean status;

    public CardAccountDto() {
    }

    
}
