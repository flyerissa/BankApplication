package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.domain.bank.Account;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.service.bank.BankService;
import com.luxoft.bankapp.ui.BankCommander;

import java.util.Scanner;

public class DepositCommand implements Command {
    @Override
    public void execute() {
        Client currentClient = BankCommander.getActiveClient();
        Account currentAccount = currentClient.getActiveAccount();
        if (currentAccount != null) {
            System.out.println("Please enter sum to deposit!");
            String sum = new Scanner(System.in).nextLine();
            BankService.depositAccount(currentClient, Double.parseDouble(sum));
            BankService.saveOrUpdateClientToDB(currentClient);
            System.out.println("Success!");
        } else {
            System.out.println("Please set active account first by accesing to corresponding menu!");
            return;
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Deposit");
    }
}
