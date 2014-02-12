package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.domain.bank.Account;

public class CheckingAccount extends AbstractAccount {
	private float overdraft;
    private float balance;

    public CheckingAccount(float balance, float overdraft){
        this.balance = balance;
        this.overdraft = balance;
    }
	public void setOverdraft(float x){
		overdraft = x;
	}

    public float getBalance(){
        return balance;
    }

    public void deposit(float x) {
        balance += x;

    }

	@Override
	public void withdraw(float x) {
		float withdraw = balance - x;
		if (withdraw >= overdraft){
			balance = withdraw;
		}
		else
			System.out.println("Withdraw is impossible!Not enough money on your account!");
	}

    @Override
    public float maximumAmountToWithdraw() {
        float sum = 0;
        if (balance > 0){
            sum = balance + overdraft;
        }
        else sum = overdraft;
        return sum;
    }
	/*@Override
	public String printReport() {
		StringBuilder sb = new StringBuilder();
		sb.append("Overdraft is: " + overdraft + ", Balance is: " + balance);
		return sb.toString();
		
	}
*/
}
