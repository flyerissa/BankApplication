package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.BankApplication;
import com.luxoft.bankapp.domain.bank.Client;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddClientCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Enter name of the bank:");
        Scanner sc = new Scanner(System.in);
        Bank bank = null;
        ArrayList<Bank> listofbanks = BankApplication.getListOfBanks();
        String bankName = sc.nextLine();
        Pattern pattern1 = Pattern.compile("^\\s*[A-Za-z]{3,}\\s*");
        Matcher matcher1 = pattern1.matcher(bankName);

        if (matcher1.matches()) {
            if (listofbanks.size() > 0) {
                for (Bank existingBank : listofbanks) {
                    if (existingBank.getName().equals(bankName)) {
                        bank = existingBank;
                    }
                }
            } else {
                bank = new Bank();
                bank.setName(bankName);
                BankApplication.addBank(bank);
            }

        } else {
            System.out.println("Bank's name is incorrect!");
            return;
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
        /*if (matcher.matches() && matcher2.matches()) {
            if (gender.equals("F")) {
                if (client != null) {
                    client.setGender(Gender.FEMALE);
                }
            } else {
                client.setGender(Gender.MALE);
            }

            System.out.println("Add account to client, enter type C or S, balance and overdraft, like as C 2000 500");
            Scanner scanner = new Scanner(System.in);
            String account1 = scanner.nextLine();
            String[] account = account1.split(" ");
            Account account2;
            if (account[0].equals("C")) {
                account2 = new CheckingAccount(Double.parseDouble(account[1]), Double.parseDouble(account[2]));
            } else {
                account2 = new SavingAccount(Double.parseDouble(account[1]));
            }
            client.addAccountToSet(account2);
        } else return;

        if (!client.equals(null)) {
            bank.addClient(client);
        }
        ClientDAOImpl clientDAO = new ClientDAOImpl();
        try {
            clientDAO.save(client);
            BankCommander.activeClient = client;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Client " + client.getFullName() + " was added to bank " + bank.getName());
    }
*/
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Add client");
    }
}
