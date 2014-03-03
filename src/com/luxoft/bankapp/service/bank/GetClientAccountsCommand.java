package com.luxoft.bankapp.service.bank;

import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.domain.bank.Account;
import com.luxoft.bankapp.ui.BankCommander;

import java.util.Scanner;

public class GetClientAccountsCommand implements Command {
    @Override
    public void execute() {
        BankService.getInstance().getAllAccounts(BankCommander.getActiveClient());
        System.out.println("Please choose active account by its id: ");
        Scanner sc = new Scanner(System.in);
        String result = sc.nextLine();
        for (Account a : BankCommander.getActiveClient().getAccounts()) {
            if (a.getId().equals(Integer.parseInt(result))) {
                BankCommander.getActiveClient().setActiveAccount(a);
            } else {
                System.out.println("Incorrect id, please retry!");
                return;
            }
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Get account info for client");
    }
}
