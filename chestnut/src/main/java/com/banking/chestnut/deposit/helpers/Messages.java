package com.banking.chestnut.deposit.helpers;

public enum Messages {
    ADD_DEPOSIT_ERROR("Error during adding deposit"),
    CREATE_DEPOSIT_TYPE_ERROR("Error during creating deposit type"),
    DELETE_DEPOSIT_TYPE_SUCCESS("Deposit type deleted"),
    ADD_DEPOSIT_TYPE_SUCCESS("Deposit type created"),
    CLOSE_DEPOSIT_SUCCESS("Deposit closed"),
    ADD_DEPOSIT_SUCCESS("Deposit created");

    
    private String message;
    
    Messages(String message) {
        this.message = message;
    }
    
    @Override
    public String toString() {
        return message;
    }
}
