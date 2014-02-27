package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.exceptions.OverdraftLimitExceededException;

import java.util.Map;

//4th exercise
public class CheckingAccount extends AbstractAccount implements Comparable {
    private double overdraft;
    private double balance;
    private double amount;

    public Double getOverdraft() {
        return overdraft;
    }

    @Override
    public String toString() {
        return "CheckingAccount{" +
                "id=" + id +
                ", balance=" + balance +
                ", amount=" + amount +
                ", overdraft=" + overdraft +
                '}';
    }

    public CheckingAccount(double balance, double overdraft) {
        if (balance >= 0 && overdraft >= 0) {
            this.balance = balance;
            this.overdraft = overdraft;
            maximumAmountToWithdraw();

        } else throw new IllegalArgumentException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckingAccount)) return false;

        CheckingAccount that = (CheckingAccount) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public void setOverdraft(double x) {

        if (x >= 0) {
            overdraft = x;
        } else throw new IllegalArgumentException();
    }

    public Double getBalance() {
        return balance;
    }

    public void deposit(double x) {
        balance += x;

    }

    @Override
    public void withdraw(double x) throws OverdraftLimitExceededException {
        double withdraw = balance - x;
        if (withdraw >= overdraft) {
            balance = withdraw;
        } else
            throw new OverdraftLimitExceededException(balance, amount);
    }

    @Override
    public double maximumAmountToWithdraw() {

        if (balance > 0) {
            amount = balance + overdraft;
        } else {
            amount = overdraft;
        }
        return amount;
    }

    @Override
    public double decimalValue() {
        return Math.round(balance);
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public int compareTo(Object o) {
        Account other = (Account) o;
        int result;
        Double thisBalance = this.getBalance();
        Double thatBalance = other.getBalance();
        result = thisBalance.compareTo(thatBalance);
        return result;
    }

    public void parseFeed(Map<String, String> feed) {
        Double overdraft = Double.parseDouble(feed.get("overdraft"));
        this.setOverdraft(overdraft);

    }
}
