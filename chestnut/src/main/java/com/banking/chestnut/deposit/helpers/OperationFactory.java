package com.banking.chestnut.deposit.helpers;

import com.banking.chestnut.models.DepositOperations;
import com.banking.chestnut.models.Deposits;
import com.banking.chestnut.models.OperationType;

import java.util.NoSuchElementException;

import static com.banking.chestnut.deposit.helpers.DateHelper.currentTimestamp;

public class OperationFactory {
    private OperationFactory(){}
    
    public static DepositOperations createOperation(OperationType operationType, Deposits deposits) {
        switch (operationType) {
            case OPENING:
                return new DepositOperations(deposits, OperationType.OPENING, currentTimestamp());
            case CLOSING:
                return new DepositOperations(deposits, OperationType.CLOSING, currentTimestamp());
            default:
                throw new NoSuchElementException();
        }
    }
}
