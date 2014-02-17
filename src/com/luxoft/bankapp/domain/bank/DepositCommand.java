package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.service.bank.BankService;

import java.util.Scanner;

/**
 * Created by User on 17.02.14.
 */
public class DepositCommand implements Command {
    @Override
    public void execute() {
        FindClientCommand findClient = new FindClientCommand();
        findClient.execute();
        System.out.println("Enter sum to deposit");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();

        BankService.depositAccount(findClient.currentClient, Double.parseDouble(input));

    }

    @Override
    public void printCommandInfo() {
        System.out.println("Deposit");
    }
}
