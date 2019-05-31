package com.banking.chestnut.credit.dto;

import com.banking.chestnut.models.CreditBalance;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by Kubek on 2019-05-30.
 */
@Data
@NoArgsConstructor
@JsonRootName(value = "CreditBalance")
public class CreditBalanceDto {

    @Getter
    private Integer id;

    private Float debt_asset;

    private Float debt_interest;

    private Long payments_left;

    public CreditBalanceDto(CreditBalance creditBalance) {
        this.id = creditBalance.getId();
        this.debt_asset = creditBalance.getDebt_asset();
        this.debt_interest = creditBalance.getDebt_interest();
        this.payments_left = creditBalance.getPayments_left();
    }
}
