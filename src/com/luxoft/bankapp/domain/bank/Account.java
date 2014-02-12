package com.luxoft.bankapp.domain.bank;

import java.math.BigDecimal;

public interface Account  {
    double getBalance();
	void deposit(double x);
	void withdraw(double x);
    double maximumAmountToWithdraw();
}
