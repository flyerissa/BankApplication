package com.luxoft.bankapp.exceptions;

//3d exercise
public class NotEnoughFundsException extends BankException {
    protected Double amount;
    protected Double balance;

    public Double getAmount() {
        return amount;
    }

    public NotEnoughFundsException(Double balance, Double amount) {
        this.balance = balance;
        this.amount = amount;
    }

    public Double getBalance() {
        return balance;
    }

}
