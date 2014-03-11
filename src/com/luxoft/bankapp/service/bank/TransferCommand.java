package com.luxoft.bankapp.service.bank;

import com.luxoft.bankapp.DAO.TransactionManager;
import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;

import java.util.Scanner;
import java.util.concurrent.Callable;

public class TransferCommand implements Command {
    @Override
    public void execute() throws Exception {
        TransactionManager tm = TransactionManager.getInstance();
        System.out.println("Please enter the name of bank: ");
        final String name = new Scanner(System.in).nextLine();
        final Bank current = BankService.getInstance().findBankByNameAndSetActive(name);
        System.out.println("Bank " + current.getName() + " was chose.");
        System.out.println("Please enter the client name to withdraw");
        final String client = new Scanner(System.in).nextLine();
        System.out.println("Please enter the sum to withdraw");
        final String sum = new Scanner(System.in).nextLine();
        System.out.println("Please enter the client name to deposit");
        final String client2 = new Scanner(System.in).nextLine();

        tm.doInTransaction(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Client clientFrom = BankService.getInstance().selectClient(current, client);
                Client clientTo = BankService.getInstance().selectClient(current, client2);
                BankService.getInstance().transfer(clientFrom, Double.parseDouble(sum), clientTo);
                BankService.getInstance().saveOrUpdateClientToDB(clientFrom);
                BankService.getInstance().saveOrUpdateClientToDB(clientTo);
                return null;
            }
        });
        System.out.println("Transfer was successful!");
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Transfer");
    }


}
