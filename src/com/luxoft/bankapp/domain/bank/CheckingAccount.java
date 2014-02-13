package com.luxoft.bankapp.domain.bank;


import com.luxoft.bankapp.service.bank.OverdraftLimitExceededException;

public class CheckingAccount extends AbstractAccount {
    private double overdraft;
    private double balance;

    public CheckingAccount(double balance, double overdraft) {
        if (balance >= 0 && overdraft >= 0) {
            this.balance = balance;
            this.overdraft = overdraft;
        } else throw new IllegalArgumentException();
    }

    public void setOverdraft(double x) {

        if (x >= 0) {
            overdraft = x;
        } else throw new IllegalArgumentException();
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double x) {
        balance += x;

    }

    @Override
    public void withdraw(double x) throws OverdraftLimitExceededException {
        double withdraw = balance - x;
        if (withdraw <= overdraft) {
            balance = withdraw;
        } else
            throw new OverdraftLimitExceededException();
    }

    @Override
    public double maximumAmountToWithdraw() {
        double sum = 0;
        if (balance > 0) {
            sum = balance + overdraft;
        } else sum = overdraft;
        return sum;
    }

}
