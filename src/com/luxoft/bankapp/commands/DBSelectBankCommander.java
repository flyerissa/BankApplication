package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.exceptions.BankNotFoundException;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.service.bank.BankService;

import java.util.Scanner;

/**
 * Created by aili on 23.02.14.
 */
public class DBSelectBankCommander implements Command {

    @Override
    public void execute() throws ClientExistsException {
        System.out.println("Please enter the name of bank: ");
        String name = new Scanner(System.in).nextLine();
        try {
            Bank current = BankService.findBankByName(name);
            System.out.println("Bank " + current.getName() + " was chose.");
        } catch (BankNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Select bank by name");
    }
}
