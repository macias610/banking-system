package com.banking.chestnut.moneytransfers.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class TransactionDTO {

    private int id;

//    @JsonFormat(pattern="dd.MM.YYYY HH:mm", timezone = "Europe/Budapest")
    private Date transactionDate;
    private long value;
    private String senderAccNumber;
    private String receiverAccNumber;
    private int senderId;
    private int receiverId;
    private String title;
}
