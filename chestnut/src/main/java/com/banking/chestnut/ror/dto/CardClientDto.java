package com.banking.chestnut.ror.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@Data
public class CardClientDto implements Serializable {

    private Integer id;
    private String number;
    private Boolean status;
    private String numberBankingAccount;

}
