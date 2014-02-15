package com.luxoft.bankapp.exceptions;

//3d exercise
public class OverdraftLimitExceededException extends NotEnoughFundsException {

    public OverdraftLimitExceededException(double bal, double amount) {
        super(bal, amount);
    }

}