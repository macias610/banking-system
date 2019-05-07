package com.banking.chestnut.ror.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AgentAccountDto implements Serializable {

    private Integer accountId;

    private Integer clientId;
}
