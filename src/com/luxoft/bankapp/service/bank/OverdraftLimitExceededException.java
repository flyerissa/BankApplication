package com.luxoft.bankapp.service.bank;

/**
 * Created by User on 13.02.14.
 */
public class OverdraftLimitExceededException extends NotEnoughFundsException {
    private double balance;

    public OverdraftLimitExceededException(double bal, double amount) {
        balance = bal;
        this.amount = amount;
    }

    public double getBalance() {
        return balance;
    }

}