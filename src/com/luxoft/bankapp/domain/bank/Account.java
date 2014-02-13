package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.service.bank.NotEnoughFundsException;

public interface Account  {
    double getBalance();
	void deposit(double x);

    void withdraw(double x) throws NotEnoughFundsException;

    double maximumAmountToWithdraw();
}
