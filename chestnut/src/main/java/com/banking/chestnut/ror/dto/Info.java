package com.banking.chestnut.ror.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Info implements Serializable {

    @JsonProperty("first_name")
    private String firstName;

    private String surname;

    private Long pesel;

    private String birthday;

    private String country;

    private String nationality;

    private String lang;
}