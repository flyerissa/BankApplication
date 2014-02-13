package com.luxoft.bankapp.domain.bank;


public class CheckingAccount extends AbstractAccount {
    private double overdraft;
    private double balance;

    public CheckingAccount(double balance, double overdraft) {
        this.balance = balance;
        this.overdraft = overdraft;
    }

    public void setOverdraft(double x) {
        overdraft = x;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double x) {
        balance += x;

    }

    @Override
    public void withdraw(double x) {
        double withdraw = balance - x;
        if (withdraw <= overdraft) {
            balance = withdraw;
        } else
            System.out.println("Withdraw is impossible!Not enough money on your account!");
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
