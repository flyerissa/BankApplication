package com.luxoft.bankapp.domain.bank;

public interface Account  {
    double getBalance();
	void deposit(double x);
	void withdraw(double x);
    double maximumAmountToWithdraw();
}
