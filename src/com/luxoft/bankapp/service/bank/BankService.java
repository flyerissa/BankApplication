package com.luxoft.bankapp.service.bank;

import com.luxoft.bankapp.DAO.BankDAOImpl;
import com.luxoft.bankapp.DAO.ClientDAOImpl;
import com.luxoft.bankapp.domain.bank.Account;
import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.BankInfo;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.exceptions.BankInfoException;
import com.luxoft.bankapp.exceptions.BankNotFoundException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.ui.BankCommander;

import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;


public class BankService {

    private static final String FILE_OBJECT_DATA = "client_serialize.data";

    private static ClientDAOImpl clientDAO = new ClientDAOImpl();

    private static BankDAOImpl bankDAO = new BankDAOImpl();

    private static BankService instance;

    private BankService() {
    }

    public static BankService getInstance() {
        if (instance == null) {
            instance = new BankService();
        }
        return instance;
    }


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

    public void saveBank(Bank bank) {
        try {
            ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(FILE_OBJECT_DATA));
            oo.writeObject(bank);
            oo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bank loadBank() {
        Bank bank = null;
        try {
            ObjectInputStream oi = new ObjectInputStream(new FileInputStream(FILE_OBJECT_DATA));
            bank = (Bank) oi.readObject();
            oi.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bank;
    }


    public void printMaximumAmountToWithdraw(Bank b) {
        for (Client c : b.getClients().values()) {
            System.out.println(c.getActiveAccount().maximumAmountToWithdraw());
        }
    }


    public Client findClientByNameAsActive(Bank bank, String name) throws ClientNotFoundException {

        Client foundClient = null;
        try {
            foundClient = new ClientDAOImpl().findClientByName(bank, name);
            if (foundClient == null) {
                throw new ClientNotFoundException("There is no such client in DB! Please retry");
            }
            BankCommander.setActiveClient(foundClient);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foundClient;
    }

    public Client selectClient(Bank bank, String name) throws ClientNotFoundException {
        Client foundClient = null;
        try {
            foundClient = new ClientDAOImpl().findClientByName(bank, name);
            if (foundClient == null) {
                throw new ClientNotFoundException("There is no such client in DB! Please retry");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return foundClient;

    }


    public List<Client> getAllClients(Bank bank) throws ClientNotFoundException {
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

    public void saveOrUpdateClientToDB(Client client) throws SQLException {
        new ClientDAOImpl().save(client);
    }

    public void removeClientFromDB(Client client) {
        try {
            new ClientDAOImpl().remove(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Bank findBankByNameAndSetActive(String name) throws BankNotFoundException, SQLException {
        Bank foundBank = null;
        foundBank = new BankDAOImpl().getBankByName(name);
        if (foundBank == null) {
            throw new BankNotFoundException("There is no such bank in DB! Please retry!");
        } else {
            BankCommander.setActiveBank(foundBank);
        }
        return foundBank;
    }

    public Bank selectBank(String name) throws SQLException, BankNotFoundException {
        Bank foundBank = null;
        foundBank = new BankDAOImpl().getBankByName(name);
        if (foundBank == null) {
            throw new BankNotFoundException("There is no such bank in DB! Please retry!");
        }
        return foundBank;
    }

    public List<Client> findClientsByNameAndCity(Bank bank, String name, String city) throws SQLException {
        return new BankDAOImpl().findClientsByNameAndCity(bank, name, city);
    }

    public Client findClientById(Integer id) throws SQLException {
        return new ClientDAOImpl().findClientById(id);
    }

    public BankInfo getBankInfo(String name) throws BankInfoException {
        BankInfo bankInfo = null;
        try {
            bankInfo = new BankDAOImpl().getBankInfo(name);
            if (bankInfo == null) {
                throw new BankInfoException("There is no info for the bank!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bankInfo;
    }


    public void getAllAccounts(Client client) {
        try {
            new ClientDAOImpl().getAllAccounts(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(client.getAccounts());

    }

    public void depositAccount(Client client, double sum) {
        Account account = client.getActiveAccount();
        account.deposit(sum);
        client.setBalanceFromDB(client.getBalance() + sum);

    }

    public synchronized void withdrawAccount(Client client, double sum) throws NotEnoughFundsException {
        Account account = client.getActiveAccount();
        account.withdraw(sum);
        client.setBalanceFromDB(client.getBalance() - sum);
    }

    public void transfer(Client from, double transfer, Client to) throws NotEnoughFundsException {
        Account accountFrom = from.getActiveAccount();
        accountFrom.withdraw(transfer);
        Account accountTo = to.getActiveAccount();
        accountTo.deposit(transfer);

    }

    public Client getClient(Bank bank, String clientName) {
        return bank.getClients().get(clientName);
    }

    public Account findAccountById(Client client, Integer id) {

        for (Account a : client.getAccounts()) {
            if (id.equals(a.getId())) {
                return a;
            }
        }
        return null;
    }

    public Account findAccountFromDB(Client client, Integer id) throws Exception {
        Set<Account> set = new ClientDAOImpl().getAllAccounts(client);
        Account current = null;
        if (set == null) {
            throw new Exception("No accounts were found!");
        }
        for (Account account : set) {
            if (account.getId().equals(id)) {
                current = account;
            }
        }
        return current;
    }
}
