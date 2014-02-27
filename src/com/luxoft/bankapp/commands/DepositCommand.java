package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.domain.bank.Account;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.service.bank.BankService;

import java.util.Scanner;
import java.util.Set;

public class DepositCommand implements Command {
    @Override
    public void execute() {
        Client current = BankCommander.getActiveClient();
        Set<Account> accountSet = current.getAccounts();
        System.out.println(accountSet);
        System.out.println("Please enter account id to deposit");
        String id = new Scanner(System.in).nextLine();
        Integer id1 = Integer.parseInt(id);
        for (Account account : accountSet) {
            if (account.getId().equals(id1)) {
                current.setActiveAccount(account);
            } else {
                System.out.println("Incorrect id, please retry");
            }
        }
        System.out.println("Please enter sum to deposit!");
        String sum = new Scanner(System.in).nextLine();
        BankService.depositAccount(current, Double.parseDouble(sum));
        BankService.saveOrUpdateClientToDB(current);
        System.out.println("Success!");

    }

    @Override
    public void printCommandInfo() {
        System.out.println("Deposit");
    }
}
