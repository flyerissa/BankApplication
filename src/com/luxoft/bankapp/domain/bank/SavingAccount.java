package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.exceptions.NotEnoughFundsException;

//3d exercise
public class SavingAccount extends AbstractAccount {
    private double balance;

    public SavingAccount(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double x) {
        balance += x;
    }

    @Override
    public void withdraw(double x) throws NotEnoughFundsException {
        if (x <= balance) {
            balance -= x;
        } else
            throw new NotEnoughFundsException(balance, balance);
    }

    @Override
    public double maximumAmountToWithdraw() {
        return balance;
    }
}
