package com.banking.chestnut.models;

public enum AccountType {
    INDIVIDUAL("INDIVIDUAL"),
    COMMERCIAL("COMMERCIAL");


    private String type;

    AccountType(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }
}
