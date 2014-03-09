package com.luxoft.bankapp.service.bank;

import com.luxoft.bankapp.DAO.TransactionManager;
import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.domain.bank.Account;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.ui.BankCommander;

import java.util.Scanner;
import java.util.concurrent.Callable;

public class DepositCommand implements Command {
    @Override
    public void execute() throws Exception {
        final Client currentClient = BankCommander.getActiveClient();
        Account currentAccount = currentClient.getActiveAccount();
        if (currentAccount != null) {
            System.out.println("Please enter sum to deposit!");
            final String sum = new Scanner(System.in).nextLine();
            TransactionManager tm = TransactionManager.getInstance();
            tm.doInTransaction(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    BankService.getInstance().depositAccount(currentClient, Double.parseDouble(sum));
                    BankService.getInstance().saveOrUpdateClientToDB(currentClient);
                    return null;
                }
            });
            System.out.println("Success!");
        } else {
            System.out.println("Please set active account first by accesing to corresponding menu!");
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Deposit");
    }
}
