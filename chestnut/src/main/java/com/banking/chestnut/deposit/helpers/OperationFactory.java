package com.banking.chestnut.deposit.helpers;

import com.banking.chestnut.models.Deposit;
import com.banking.chestnut.models.Operation;
import com.banking.chestnut.models.OperationType;

import java.util.NoSuchElementException;

import static com.banking.chestnut.deposit.helpers.DateHelper.currentTimestamp;

public class OperationFactory {
    
    public static Operation createOperation(OperationType operationType, Deposit deposit){
        switch (operationType){
            case OPENING:
                return new Operation(deposit,OperationType.OPENING, currentTimestamp());
            case CLOSING:
                return new Operation(deposit,OperationType.CLOSING, currentTimestamp());
                default:
                    throw new NoSuchElementException();
        }
    }
}
