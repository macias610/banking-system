package com.banking.chestnut.ror.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AgentAccountDto implements Serializable {

    @JsonProperty("account_id")
    private Integer accountId;

    @JsonProperty("client_id")
    private Integer clientId;
}
