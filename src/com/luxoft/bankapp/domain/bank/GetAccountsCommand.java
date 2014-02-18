package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.service.bank.BankService;

import java.util.Scanner;

/**
 * Created by User on 17.02.14.
 */
public class GetAccountsCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Enter name of client");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        for (Bank b : BankApplication.listOfBanks) {
            for (Client c : b.getClients()) {
                if (c.getFullName().equals(name)) {
                    BankService.getAccount(c);
                } else {
                    System.out.println("There is no such client!");
                }
            }
        }

    }

    @Override
    public void printCommandInfo() {
        System.out.println("Get account info for client");
    }
}
