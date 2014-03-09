package com.luxoft.bankapp.service.bank;

import com.luxoft.bankapp.DAO.TransactionManager;
import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.domain.bank.Account;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.ui.BankCommander;

import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Callable;

public class WithdrawCommand implements Command {
    @Override
    public void execute() throws Exception {
        final Client current = BankCommander.getActiveClient();
        Set<Account> accountSet = current.getAccounts();
        System.out.println(accountSet);
        System.out.println("Please enter account id to withdraw");
        String id = new Scanner(System.in).nextLine();
        Integer id1 = Integer.parseInt(id);
        for (Account account : accountSet) {
            if (account.getId().equals(id1)) {
                current.setActiveAccount(account);
            } else {
                System.out.println("Incorrect id, please retry");
            }
        }
        System.out.println("Please enter sum to withdraw!");
        final String sum = new Scanner(System.in).nextLine();
        try {
            TransactionManager tm = TransactionManager.getInstance();
            tm.doInTransaction(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    BankService.getInstance().withdrawAccount(current, Double.parseDouble(sum));
                    BankService.getInstance().saveOrUpdateClientToDB(current);
                    return null;
                }
            });
            System.out.println("Success!Balance is " + current.getBalance());
        } catch (NotEnoughFundsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Withdraw");
    }
}


