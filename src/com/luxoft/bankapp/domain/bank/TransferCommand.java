package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.service.bank.BankService;

import java.util.Scanner;

/**
 * Created by User on 17.02.14.
 */
public class TransferCommand implements Command {
    @Override
    public void execute() {
        FindClientCommand fromClient = new FindClientCommand();
        fromClient.execute();
        FindClientCommand toClient = new FindClientCommand();
        toClient.execute();

        System.out.println("Enter sum to transfer");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        try {
            BankService.transfer(fromClient.currentClient, Double.parseDouble(input), toClient.currentClient);
        } catch (NotEnoughFundsException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void printCommandInfo() {
        System.out.println("Transfer");
    }
}
