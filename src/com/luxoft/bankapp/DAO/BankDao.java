package com.luxoft.bankapp.DAO;

import com.luxoft.bankapp.domain.bank.Bank;

import java.sql.SQLException;

public interface BankDao {
    Bank getBankByName(String name) throws SQLException;
}
