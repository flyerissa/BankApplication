package com.luxoft.bankapp.service.bank;

import com.luxoft.bankapp.DAO.TransactionManager;
import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.ui.BankCommander;

import java.util.Scanner;
import java.util.concurrent.Callable;

/**
 * Created by aili on 23.02.14.
 */
public class DBSelectClientCommander implements Command {

    @Override
    public void execute() throws Exception {
        if (BankCommander.getActiveBank() == null) {
            new DBSelectBankCommander().execute();
        }
        final Bank currentBank = BankCommander.getActiveBank();
        System.out.println("Please enter the client name");
        final String name = new Scanner(System.in).nextLine();
        TransactionManager tm = TransactionManager.getInstance();
        Client client = tm.doInTransaction(new Callable<Client>() {
            @Override
            public Client call() throws Exception {
                return BankService.getInstance().findClientByNameAsActive(currentBank, name);
            }
        });
        client.setBank(BankCommander.getActiveBank());
        System.out.println("Client" + client.getFullName() + " was selected");
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Select client by name");
    }
}

