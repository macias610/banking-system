package com.banking.chestnut.ror.dto;

import com.banking.chestnut.models.Contacts;
import com.banking.chestnut.models.Documents;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SaveClient implements Serializable {

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("pesel")
    private Long pesel;

    @JsonProperty("street")
    private String street;

    @JsonProperty("house_number")
    private String houseNumber;

    @JsonProperty("apartment_number")
    private String apartmentNumber;

    @JsonProperty("city")
    private String city;

    @JsonProperty("zip")
    private String zip;

    @JsonProperty("country")
    private String country;

    @JsonProperty("contacts")
    private List<Contacts> contacts = new ArrayList<>();

    @JsonProperty("documents")
    private List<Documents> documents = new ArrayList<>();

}
