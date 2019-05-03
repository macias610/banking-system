package com.banking.chestnut.credit.dto;

import com.banking.chestnut.models.Account;
import com.banking.chestnut.models.Credits;
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

    private Integer id;

    private Integer accountId;

    private Integer creditTypeId;

    private Integer paymentScheduleId;

    private Long value;

    private Boolean isActive;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date created_at;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expiration_at;


    public CreditDto(Credits credits) {
        this.id = credits.getId();
        this.accountId = credits.getAccount().getId();
        this.creditTypeId = credits.getCreditType().getId();
        this.paymentScheduleId = credits.getPaymentSchedule().getId();
        this.value = credits.getValue();
        this.isActive = credits.getIsActive();
        this.created_at = credits.getCreated_at();
        this.expiration_at = credits.getExpiration_at();
    }
}
