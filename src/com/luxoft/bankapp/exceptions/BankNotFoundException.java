package com.luxoft.bankapp.exceptions;

/**
 * Created by User on 27.02.14.
 */
public class BankNotFoundException extends Exception {
    public BankNotFoundException(String message) {
        super(message);
    }
}
