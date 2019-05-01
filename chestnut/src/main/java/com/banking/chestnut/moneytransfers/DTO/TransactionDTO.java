package com.banking.chestnut.moneytransfers.DTO;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class TransactionDTO {

    private int id;
    private LocalDate transactionDate;
    private long value;
    private String senderAccNumber;
    private String receiverAccNumber;
    private int senderId;
    private int receiverId;
    private String title;
}
