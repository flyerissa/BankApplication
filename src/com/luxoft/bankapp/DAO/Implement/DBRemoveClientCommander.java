package com.luxoft.bankapp.DAO.Implement;

import com.luxoft.bankapp.commands.BankCommander;

/**
 * Created by aili on 23.02.14.
 */
public class DBRemoveClientCommander {
    public static void removeClient() {
        ClientDAOImpl clientDAO = new ClientDAOImpl();
        clientDAO.remove(BankCommander.getActiveClient());

    }
}
