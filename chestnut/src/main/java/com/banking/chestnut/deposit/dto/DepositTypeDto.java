package com.banking.chestnut.deposit.dto;

import com.banking.chestnut.models.CapitalizationType;
import com.banking.chestnut.models.DepositTypes;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonRootName(value = "DepositType")
public class DepositTypeDto {
    
    @Getter
    private Integer id;
    
    private String name;
    
    private Float maxAmount;
    
    private Float minAmount;
    
    private Float interestRate;
    
    private Integer daysPeriod;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private CapitalizationType capitalizationType;
    
    public DepositTypeDto(DepositTypes depositTypes) {
        this.id = depositTypes.getId();
        this.name = depositTypes.getName();
        this.maxAmount = depositTypes.getMaxAmount();
        this.minAmount = depositTypes.getMinAmount();
        this.interestRate = depositTypes.getInterestRate();
        this.daysPeriod = depositTypes.getDaysPeriod();
        this.capitalizationType = depositTypes.getCapitalization().getType();
    }
}