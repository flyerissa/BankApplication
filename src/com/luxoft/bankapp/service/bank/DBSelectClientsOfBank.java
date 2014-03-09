package com.luxoft.bankapp.service.bank;

import com.luxoft.bankapp.DAO.TransactionManager;
import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.ui.BankCommander;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by User on 27.02.14.
 */
public class DBSelectClientsOfBank implements Command {
    @Override
    public void execute() throws Exception {
        new DBSelectBankCommander().execute();
        final Bank current = BankCommander.getActiveBank();
        try {
            TransactionManager tm = TransactionManager.getInstance();
            List<Client> listClients = tm.doInTransaction(new Callable<List<Client>>() {
                @Override
                public List<Client> call() throws Exception {
                    return BankService.getInstance().getAllClients(current);
                }
            });
            System.out.println(listClients);
        } catch (ClientNotFoundException e) {
            e.getMessage();
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Show all clients of the bank");
    }
}
