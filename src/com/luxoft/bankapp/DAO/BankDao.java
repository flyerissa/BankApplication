package com.luxoft.bankapp.DAO;

import com.luxoft.bankapp.domain.bank.Bank;

import java.sql.SQLException;


public interface BankDao {
    /**
     * Finds Bank by its name.
     * Do not load the list of the clients.
     *
     * @param name
     * @return
     */
    Bank getBankByName(String name) throws SQLException;
}
