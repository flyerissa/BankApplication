package com.luxoft.bankapp.domain.bank;

public interface Account  {

	void deposit(float x);
	void withdraw(float x);
    float maximumAmountToWithdraw();
}
