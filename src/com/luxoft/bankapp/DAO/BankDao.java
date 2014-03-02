package com.luxoft.bankapp.DAO;

import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.BankInfo;

import java.sql.SQLException;

public interface BankDao {
    Bank getBankByName(String name) throws SQLException;

    BankInfo getBankInfo(String name) throws SQLException;
}
