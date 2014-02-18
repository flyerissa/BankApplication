package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.service.bank.BankService;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 17.02.14.
 */
public class FindClientCommand implements Command {

    public Bank bank = null;
    public Client currentClient = null;


    @Override
    public void execute() {
        ArrayList<Bank> banklist = BankApplication.getListOfBanks();
        System.out.println("Enter name of the bank: ");
        Scanner sc = new Scanner(System.in);

        String bankName = sc.nextLine();
        Pattern pattern1 = Pattern.compile("^\\s*[A-Za-z]{3,}\\s*");
        Matcher matcher1 = pattern1.matcher(bankName);


        if (matcher1.matches()) {

            for (Bank existingBank : banklist) {
                if (existingBank.getName().equals(bankName)) {
                    bank = existingBank;
                } else {
                    System.out.println("Bank's name is incorrect!");
                }
            }
        }
        currentClient = BankService.findClientByName(bank);
        System.out.println("Current client is: " + currentClient);

    }

    @Override
    public void printCommandInfo() {
        System.out.println("Find client by name");
    }
}
