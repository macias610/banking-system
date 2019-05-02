package com.banking.chestnut.ror.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class LocationDto implements Serializable {

    private Integer id;

    private String zip;

    private String street;

    private String city;

    private String houseNumber;

    private String apartmentNumber;

    private Date createdAt;

}
