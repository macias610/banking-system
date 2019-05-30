package com.banking.chestnut.credit.dto;

import com.banking.chestnut.models.CreditType;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by Kubek on 2019-05-29.
 */
@Data
@NoArgsConstructor
@JsonRootName(value = "CreditType")
public class CreditTypeDto {

    @Getter
    private Integer id;

    private String name;

    private String info;

    private Float min_value;

    private Float max_value;

    private String loan_period;

    private Float interest_rate;

    public CreditTypeDto(CreditType creditType){
        this.id = creditType.getId();
        this.name = creditType.getName();
        this.info = creditType.getInfo();
        this.min_value = creditType.getMin_value();
        this.max_value = creditType.getMax_value();
        this.loan_period = creditType.getLoan_period();
        this.interest_rate = creditType.getInterest_rate();
    }
}
