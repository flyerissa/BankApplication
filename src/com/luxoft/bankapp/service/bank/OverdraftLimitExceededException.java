package com.luxoft.bankapp.service.bank;

/**
 * Created by User on 13.02.14.
 */
public class OverdraftLimitExceededException extends NotEnoughFundsException {
    private Object object;
    private double balance;

    public OverdraftLimitExceededException(Object o, double bal, double amount) {
        object = o;
        balance = bal;
        this.amount = amount;
    }

    public double getBalance() {
        return balance;
    }


    public Object getObject() {
        return object;
    }


}