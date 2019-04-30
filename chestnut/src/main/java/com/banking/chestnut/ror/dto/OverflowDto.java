package com.banking.chestnut.ror.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OverflowDto implements Serializable {

    private String type;

    private Long value;

    private Integer senderId;

    private Integer receiverId;

}
