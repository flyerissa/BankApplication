package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.DAO.Implement.ClientDAOImpl;

import java.sql.SQLException;


public class DBRemoveClientCommander {
    public static void removeClient() {
        ClientDAOImpl clientDAO = new ClientDAOImpl();
        try {
            clientDAO.remove(BankCommander.getActiveClient());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
