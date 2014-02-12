package com.luxoft.bankapp.domain.bank;

public interface Account  {
    float getBalance();
	void deposit(float x);
	void withdraw(float x);
    float maximumAmountToWithdraw();
}
