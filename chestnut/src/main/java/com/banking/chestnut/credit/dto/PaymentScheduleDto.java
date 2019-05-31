package com.banking.chestnut.credit.dto;

import com.banking.chestnut.models.PaymentSchedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * Created by Kubek on 2019-05-31.
 */
@Data
@NoArgsConstructor
@JsonRootName(value = "PayentSchedule")
public class PaymentScheduleDto {

    @Getter
    private Integer id;

    private Integer creditId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date payment_date;

    private Float payment_assets;

    private Float payment_interest;

    private boolean isActive;

    public PaymentScheduleDto(PaymentSchedule paymentSchedule) {
        this.id = paymentSchedule.getId();
        this.creditId = paymentSchedule.getCredit().getId();
        this.payment_date = paymentSchedule.getPayment_date();
        this.payment_assets = paymentSchedule.getPayment_assets();
        this.payment_interest = paymentSchedule.getPayment_interest();
        this.isActive = paymentSchedule.isActive();
    }
}
