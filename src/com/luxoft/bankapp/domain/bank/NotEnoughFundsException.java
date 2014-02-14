package com.luxoft.bankapp.domain.bank;

/**
 * Created by User on 13.02.14.
 */
public class NotEnoughFundsException extends BankException {
    protected double amount;

    public double getAmount() {
        return amount;
    }
}
