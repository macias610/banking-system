package com.banking.chestnut.ror.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class TransactionAccountDto implements Serializable {

    private Long id;
    private TransactionSideDto sender;
    private TransactionSideDto receiver;

//    @JsonFormat(pattern="dd.MM.YYYY HH:mm", timezone = "Europe/Budapest")
    private Date createdAt;
    private String title;
    private String type;
    private Long value;
}
