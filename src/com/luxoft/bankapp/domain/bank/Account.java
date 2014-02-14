package com.luxoft.bankapp.domain.bank;

//3d exercise
public interface Account  {
    double getBalance();
	void deposit(double x);

    void withdraw(double x) throws OverdraftLimitExceededException, NotEnoughFundsException;

    double maximumAmountToWithdraw();
}
