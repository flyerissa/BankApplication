package com.luxoft.bankapp.domain.bank;

//3d exercise
public class OverdraftLimitExceededException extends NotEnoughFundsException {

    public OverdraftLimitExceededException(double bal, double amount) {
        super(bal, amount);
    }

}