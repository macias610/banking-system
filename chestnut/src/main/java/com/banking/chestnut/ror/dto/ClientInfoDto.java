package com.banking.chestnut.ror.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ClientInfoDto implements Serializable {

    private Integer id;
    private Info info;

    public ClientInfoDto(Integer id, Info info) {
        this.id = id;
        this.info = info;
    }
}

