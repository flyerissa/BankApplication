package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.domain.bank.Account;

public class SavingAccount extends AbstractAccount {
    private double balance;
    public SavingAccount(double balance){
        this.balance = balance;
    }

    public double getBalance(){
        return balance;
    }

    public void deposit(double x) {
        balance += x;

    }

	@Override
	public void withdraw(double x) {
		if (x <= balance){
			balance -= x;
		}
		
		else
			System.out.println("Withdraw is impossible!Not enough money on your account!");
	}

    @Override
    public double maximumAmountToWithdraw() {
        return balance;
    }

    /*@Override
	public String printReport() {
		StringBuilder sb = new StringBuilder();
		sb.append("Balance is: " + balance);
		return sb.toString();
	}
*/
}