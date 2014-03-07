package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.exceptions.NotEnoughFundsException;

//4th exercise
public class SavingAccount extends AbstractAccount implements Comparable {
    private double balance;


    @Override
    public String toString() {
        return "SavingAccount{" +
                "balance=" + balance +
                ", id=" + id +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SavingAccount)) return false;

        SavingAccount that = (SavingAccount) o;

        if (Double.compare(that.balance, balance) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(balance);
        return (int) (temp ^ (temp >>> 32));
    }

    public SavingAccount(double balance) {
        this.balance = balance;

    }

    public Double getBalance() {
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

    @Override
    public double decimalValue() {
        return Math.round(balance);
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
}
