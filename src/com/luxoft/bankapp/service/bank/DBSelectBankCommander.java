package com.luxoft.bankapp.service.bank;

import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.exceptions.BankNotFoundException;

import java.util.Scanner;

/**
 * Created by aili on 23.02.14.
 */
public class DBSelectBankCommander implements Command {

    @Override
    public void execute() {
        System.out.println("Please enter the name of bank: ");
        String name = new Scanner(System.in).nextLine();
        Bank current;
        try {
            current = BankService.findBankByName(name);
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
