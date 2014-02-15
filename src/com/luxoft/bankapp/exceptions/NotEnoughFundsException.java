package com.luxoft.bankapp.exceptions;

//3d exercise
public class NotEnoughFundsException extends BankException {
    protected double amount;
    protected double balance;

    public double getAmount() {
        return amount;
    }

    public NotEnoughFundsException(double balance, double amount) {
        this.balance = balance;
        this.amount = amount;
    }

    public double getBalance() {
        return balance;
    }

}
