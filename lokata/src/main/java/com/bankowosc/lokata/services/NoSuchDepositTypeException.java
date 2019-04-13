package com.bankowosc.lokata.services;

public class NoSuchDepositTypeException extends Exception {
    
    public NoSuchDepositTypeException(String message) {
        super(message);
    }
}
