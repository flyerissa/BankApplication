package com.luxoft.bankapp.service.bank;

import com.luxoft.bankapp.domain.bank.Account;
import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;

import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//3d exercise
public class BankService {
    public static void addClient(Bank bank, Client client) throws ClientExistsException {
        int size = bank.getClients().size();
        String nextName = client.getFullName();
        Set<Client> list = bank.getClients();

        if (size > 0) {
            for (Client existingClient : list) {
                if (existingClient.getFullName().equals(nextName)) {
                    throw new ClientExistsException();
                }
            }

            list.add(client);

        } else
            list.add(client);
    }


    public static void printMaximumAmountToWithdraw(Bank b) {
        for (Client c : b.getClients()) {
            System.out.println(c.getActiveAccount().maximumAmountToWithdraw());
        }
    }

    public static Client findClientByName(Bank bank) {
        System.out.println("Enter client name to continue: ");
        Scanner sc = new Scanner(System.in);
        String client = sc.nextLine();

        Pattern pattern =
                Pattern.compile(
                        "^\\s*[A-Za-z]{2,}[ ]*[A-Za-z]{2,}\\s*$");
        Matcher matcher = pattern.matcher(client);

        Client foundClient = null;
        boolean matches = matcher.matches();

        if (matches) {
            for (Client existingClient : bank.getClients()) {
                if (existingClient.getFullName().equals(client)) {
                    foundClient = existingClient;
                }
            }
        } else {
            System.out.println("There is no such client!");
        }

        return foundClient;
    }

    public static void getAccount(Client client) {
        Account account = client.getActiveAccount();
        double balance = account.getBalance();
        System.out.println(client + "Balance is: " + balance);

    }

    public static void depositAccount(Client client, double sum) {
        Account account = client.getActiveAccount();
        account.deposit(sum);

    }

    public static void withdrawAccount(Client client, double sum) throws NotEnoughFundsException {
        Account account = client.getActiveAccount();
        account.withdraw(sum);
    }

    public static void transfer(Client from, double transfer, Client to) throws NotEnoughFundsException {
        Account accountFrom = from.getActiveAccount();
        accountFrom.withdraw(transfer);
        Account accountTo = to.getActiveAccount();
        accountTo.deposit(transfer);

    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_PHONE_NUMBER_REGEX =
            Pattern.compile("^(\\+38)?(\\(?0\\d{2}\\)?[\\- ]?)?\\d{3}[\\- ]?\\d{2}[\\- ]?\\d{2}$");

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static boolean validatePhone(String phoneString) {
        Matcher matcher = VALID_PHONE_NUMBER_REGEX.matcher(phoneString);
        return matcher.find();
    }
}
