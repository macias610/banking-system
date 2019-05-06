package com.banking.chestnut.moneytransfers.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionDTO {

    private int id;
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern="dd.MM.yyyy")
    private LocalDate transactionDate;
    private long value;
    private String senderAccNumber;
    private String receiverAccNumber;
    private int senderId;
    private int receiverId;
    private String title;
}