package com.luxoft.bankapp.service.bank;

import com.luxoft.bankapp.DAO.BankDAOImpl;
import com.luxoft.bankapp.DAO.ClientDAOImpl;
import com.luxoft.bankapp.commands.BankCommander;
import com.luxoft.bankapp.domain.bank.Account;
import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.BankInfo;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.exceptions.BankInfoException;
import com.luxoft.bankapp.exceptions.BankNotFoundException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;

import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

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


    public static Client findClientByName(Bank bank) throws ClientNotFoundException {
        System.out.println("Enter client name to continue: ");
        Scanner sc = new Scanner(System.in);
        String client = sc.nextLine();
        Client foundClient = null;
        try {
            foundClient = new ClientDAOImpl().findClientByName(bank, client);
            if (foundClient == null) {
                throw new ClientNotFoundException("There is no such client in DB! Please retry");
            } else {
                BankCommander.setActiveClient(foundClient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foundClient;
    }


    public static List<Client> getAllClients(Bank bank) throws ClientNotFoundException {
        List<Client> listClients = null;
        try {
            listClients = new ClientDAOImpl().getAllClients(bank);
            if (listClients == null) {
                throw new ClientNotFoundException("There isn't any client for bank " + bank.getName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listClients;

    }

    public static void saveOrUpdateClientToDB(Client client) {
        try {
            new ClientDAOImpl().save(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeClientFromDB(Client client) {
        try {
            new ClientDAOImpl().remove(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Bank findBankByName(String name) throws BankNotFoundException {
        Bank foundBank = null;
        try {
            foundBank = new BankDAOImpl().getBankByName(name);
            if (foundBank == null) {
                throw new BankNotFoundException("There is no such bank in DB! Please retry!");
            } else {
                BankCommander.setActiveBank(foundBank);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foundBank;
    }

    public static BankInfo getBankInfo() throws BankInfoException {
        BankInfo bankInfo = null;
        try {
            bankInfo = new BankDAOImpl().getBankInfo();
            if (bankInfo == null) {
                throw new BankInfoException("There is no info for the bank!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bankInfo;
    }

    public static BankInfo getBankInfoByBankName(String name) throws BankInfoException {
        BankInfo bankInfo = null;
        try {
            bankInfo = new BankDAOImpl().getInfoByBankName(name);
            if (bankInfo == null) {
                throw new BankInfoException("There is no info for the bank!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bankInfo;
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


    public static Client getClient(Bank bank, String clientName) {
        return bank.getClients().get(clientName);
    }

}
