package com.banking.chestnut.credit.helpers;

public enum Messages {
    ADD_CREDIT_ERROR("Error during adding credit"),
    CREATE_CREDIT_TYPE_ERROR("Error during creating credit type"),
    DELETE_CREDIT_TYPE_SUCCESS("Credit type deleted"),
    DELETE_CREDIT_TYPE_ERROR("Credit type not found"),
    ADD_CREDIT_TYPE_SUCCESS("Credit type created"),
    CLOSE_CREDIT_SUCCESS("Credit closed successfully"),
    CLOSE_CREDIT_ERROR("Error while closing Credit"),
    REMIT_CREDIT_SUCCESS("Credit remitted successfully"),
    ADD_CREDIT_SUCCESS("Credit created");

    private String message;
    Messages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}