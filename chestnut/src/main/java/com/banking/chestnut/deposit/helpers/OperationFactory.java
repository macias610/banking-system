package com.banking.chestnut.deposit.helpers;

import com.banking.chestnut.models.Deposit;
import com.banking.chestnut.models.Operation;
import com.banking.chestnut.models.OperationType;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.NoSuchElementException;

public class OperationFactory {
    
    public static Operation createOperation(OperationType operationType, Deposit deposit){
        switch (operationType){
            case OPENING:
                return new Operation(deposit,OperationType.OPENING, currentDate());
            case CLOSING:
                return new Operation(deposit,OperationType.CLOSING,currentDate());
                default:
                    throw new NoSuchElementException();
        }
    }
    
    private static Date currentDate(){
        return Date.valueOf(LocalDate.now(ZoneId.of("Europe/Warsaw")));
    }
}
