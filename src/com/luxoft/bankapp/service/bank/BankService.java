package com.luxoft.bankapp.service.bank;

import com.luxoft.bankapp.domain.bank.Account;
import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;

import java.io.*;

//3d exercise
public class BankService {

    private static final String FILE_OBJECT_DATA = "client_serialize.data";

    public void saveClient(Client c) {
        try {
            ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(FILE_OBJECT_DATA));
            oo.writeObject(c);
            oo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Client loadClient() {
        Client client = null;
        try {
            ObjectInputStream oi = new ObjectInputStream(new FileInputStream(FILE_OBJECT_DATA));
            client = (Client) oi.readObject();
            oi.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return client;
    }

    /*public static void addClient(Bank bank, Client client) throws ClientExistsException {
        int size = bank.getClients().size();
        String nextName = client.getFullName();
        Map<String, Client> list = bank.getClients();

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

*/
    public static void printMaximumAmountToWithdraw(Bank b) {
        for (Client c : b.getClients().values()) {
            System.out.println(c.getActiveAccount().maximumAmountToWithdraw());
        }
    }


    /*public static Client findClientByName(Bank bank) {
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
*/
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


    public static Client getClient(Bank bank, String clientName) {
        return bank.getClients().get(clientName);
    }

}
