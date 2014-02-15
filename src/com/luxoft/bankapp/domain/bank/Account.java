package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.exceptions.OverdraftLimitExceededException;

//3d exercise
public interface Account {
    double getBalance();

    void deposit(double x);

    void withdraw(double x) throws OverdraftLimitExceededException, NotEnoughFundsException;

    double maximumAmountToWithdraw();
}
