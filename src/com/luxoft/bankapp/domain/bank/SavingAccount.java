package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.domain.bank.Account;

public class SavingAccount extends AbstractAccount {
    private float balance;
    public SavingAccount(float balance){
        this.balance = balance;
    }

    public float getBalance(){
        return balance;
    }

    public void deposit(float x) {
        balance += x;

    }

	@Override
	public void withdraw(float x) {
		if (x <= balance){
			balance -= x;
		}
		
		else
			System.out.println("Withdraw is impossible!Not enough money on your account!");
	}

    @Override
    public float maximumAmountToWithdraw() {
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
