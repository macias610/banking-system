package com.banking.chestnut.ror.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TransactionDto implements Serializable {

    private Date startDate;
    private Date endDate;
    private String type;
}
