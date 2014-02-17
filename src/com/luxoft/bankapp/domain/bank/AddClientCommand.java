package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.service.bank.BankService;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 17.02.14.
 */
public class AddClientCommand implements Command {
    @Override
    public void execute() throws ClientExistsException {
        System.out.println("Enter name of the bank:");
        Scanner sc = new Scanner(System.in);
        Bank bank = null;
        String bankName = sc.nextLine();
        Pattern pattern1 = Pattern.compile("^\\s*[A-Za-z]{3,}\\s*");
        Matcher matcher1 = pattern1.matcher(bankName);


        if (matcher1.matches()) {

            for (Bank existingBank : BankApplication.listOfBanks) {
                if (existingBank.getName().equals(bankName)) {
                    bank = existingBank;
                } else {
                    System.out.println("Bank's name is incorrect!");
                }
            }
        }

        System.out.println("Enter name of the client to be added to bank");
        Scanner sc1 = new Scanner(System.in);
        String clientName = sc1.nextLine();
        Client client = null;
        Pattern pattern =
                Pattern.compile(
                        "^\\s*[A-Za-z]{2,}[ ]*[A-Za-z]{2,}\\s*$");
        Matcher matcher = pattern.matcher(clientName);
        System.out.println("Enter client's gender - F or M");
        Scanner sc2 = new Scanner(System.in);
        String gender = sc2.nextLine();
        Pattern pattern2 = Pattern.compile("F|M");
        Matcher matcher2 = pattern2.matcher(gender);
        if (matcher.matches() && matcher2.matches()) {
            client = new Client(clientName, Gender.valueOf(gender));
            System.out.println("Add account to client, enter type C or S, balance and overdraft, like as C 2000 500");
            Scanner scanner = new Scanner(System.in);
            String account1 = scanner.nextLine();
            String[] account = account1.split(" ");
            client.addAccount(account[0], Double.parseDouble(account[1]), Double.parseDouble(account[2]));
        }

        BankService.addClient(bank, client);
    }

    @Override
    public void printCommandInfo() {

    }
}
