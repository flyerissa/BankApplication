package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.exceptions.OverdraftLimitExceededException;

import java.util.Map;

//3d exercise
public interface Account {
    Double getBalance();

    void deposit(double x);

    void withdraw(double x) throws OverdraftLimitExceededException, NotEnoughFundsException;

    double maximumAmountToWithdraw();

    double decimalValue();

    void setId(Integer id);

    Integer getId();

    void parseFeed(Map<String, String> feed);

    String getAccountType();

}
