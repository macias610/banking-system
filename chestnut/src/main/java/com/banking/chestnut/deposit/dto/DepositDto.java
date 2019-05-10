package com.banking.chestnut.deposit.dto;

import com.banking.chestnut.models.Deposits;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@JsonRootName(value = "Deposit")
public class DepositDto {
    
    private Integer id;
    
    private Integer accountId;
    
    private Integer depositTypeId;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    
    private Float amount;
    
    private Boolean isActive;
    
    
    public DepositDto(Deposits deposits) {
        this.id = deposits.getId();
        this.accountId = deposits.getAccount().getId();
        this.depositTypeId = deposits.getDepositType().getId();
        this.startDate = deposits.getStartDate();
        this.endDate = deposits.getEndDate();
        this.amount = deposits.getAmount();
        this.isActive = deposits.getIsActive();
    }
}
