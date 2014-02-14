package com.luxoft.bankapp.domain.bank;

//3d exercise
public class NotEnoughFundsException extends BankException {
    protected double amount;
    protected double balance;

    public double getAmount() {
        return amount;
    }

    public NotEnoughFundsException(double bal, double amount) {
        balance = bal;
        this.amount = amount;
    }

    public double getBalance() {
        return balance;
    }

}
