package com.banking.chestnut.credit.dto;

import com.banking.chestnut.models.Account;
import com.banking.chestnut.models.Credits;
import com.banking.chestnut.models.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@NoArgsConstructor
@JsonRootName(value = "Credit")
public class CreditDto {

    private Integer accountId;

    private Integer creditTypeId;

    private Long value;

    private Boolean isActive;


    public CreditDto(Credits credits) {
        this.accountId = credits.getAccount().getId();
        this.creditTypeId = credits.getCreditType().getId();
        this.value = credits.getValue();
        this.isActive = credits.getIsActive();
    }
}
