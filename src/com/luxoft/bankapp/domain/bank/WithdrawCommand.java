package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.service.bank.BankService;

import java.util.Scanner;

/**
 * Created by User on 17.02.14.
 */
public class WithdrawCommand implements Command {
    @Override
    public void execute() {
        FindClientCommand findClient = new FindClientCommand();
        findClient.execute();
        System.out.println("Enter sum to withdraw");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        try {
            BankService.withdrawAccount(findClient.currentClient, Double.parseDouble(input));
        } catch (NotEnoughFundsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printCommandInfo() {

    }
}
