package com.luxoft.bankapp.service.bank;

import com.luxoft.bankapp.DAO.TransactionManager;
import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.ui.BankCommander;

import java.util.concurrent.Callable;


public class DBRemoveClientCommander implements Command {

    @Override
    public void execute() throws Exception {
        TransactionManager tm = TransactionManager.getInstance();
        tm.doInTransaction(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Client client = BankCommander.getActiveClient();
                BankService.getInstance().removeClientFromDB(client);
                return null;
            }
        });
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Remove client");
    }
}
