package com.luxoft.bankapp.service.bank;

import com.luxoft.bankapp.DAO.TransactionManager;
import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.exceptions.BankNotFoundException;

import java.util.Scanner;
import java.util.concurrent.Callable;

/**
 * Created by aili on 23.02.14.
 */
public class DBSelectBankCommander implements Command {

    @Override
    public void execute() throws Exception {
        System.out.println("Please enter the name of bank: ");
        final String name = new Scanner(System.in).nextLine();
        Bank current;
        try {
            TransactionManager tm = TransactionManager.getInstance();
            current = tm.doInTransaction(new Callable<Bank>() {
                @Override
                public Bank call() throws Exception {
                    return BankService.getInstance().findBankByNameAndSetActive(name);
                }
            });
            System.out.println("Bank " + current.getName() + " was chose.");
        } catch (BankNotFoundException e) {
            e.getMessage();
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Select bank by name");
    }
}
