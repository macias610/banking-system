package com.banking.chestnut.deposit.helpers;

public enum ErrorMessages {
    ADD_DEPOSIT_ERROR("Error during adding deposit"),
    COMMON_DEPOSIT_TYPE_ERROR("Error during processing deposit"),
    CREATE_DEPOSIT_TYPE_ERROR("Error during creating type of deposit"),
    COMMON_OPERATIONS_ERROR("Error during processing deposit");
    
    private String errorMessage;
    ErrorMessages(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    @Override
    public String toString() {
        return errorMessage;
    }
}
