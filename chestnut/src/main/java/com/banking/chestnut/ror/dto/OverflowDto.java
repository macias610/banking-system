package com.banking.chestnut.ror.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class OverflowDto implements Serializable {

    private String type;

    private Long value;

    @JsonProperty("account_id")
    private Integer accountId;

}
