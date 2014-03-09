package com.luxoft.bankapp.service.bank;

import com.luxoft.bankapp.DAO.TransactionManager;
import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.ui.BankCommander;

import java.util.concurrent.Callable;

/**
 * Created by User on 27.02.14.
 */
public class DBSaveClient implements Command {
    @Override
    public void execute() throws Exception {
        final Client client = BankCommander.getActiveClient();
        TransactionManager tm = TransactionManager.getInstance();
        tm.doInTransaction(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                BankService.getInstance().saveOrUpdateClientToDB(client);
                return null;
            }
        });
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Save or update client");
    }
}
