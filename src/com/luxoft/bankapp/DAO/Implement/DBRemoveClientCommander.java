package com.luxoft.bankapp.DAO.Implement;

import com.luxoft.bankapp.commands.BankCommander;


public class DBRemoveClientCommander {
    public static void removeClient() {
        ClientDAOImpl clientDAO = new ClientDAOImpl();
        clientDAO.remove(BankCommander.getActiveClient());
    }
}
