package com.banking.chestnut.ror.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class AccountSaveDto implements Serializable {

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("client_id")
    private Integer clientId;
}
