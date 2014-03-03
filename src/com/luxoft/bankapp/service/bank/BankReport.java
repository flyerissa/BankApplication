package com.luxoft.bankapp.service.bank;


import com.luxoft.bankapp.domain.bank.Account;
import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;

import java.util.*;

public class BankReport {

    private static BankReport instance;

    private BankReport() {
    }

    public static BankReport getInstance() {
        if (instance == null) {
            instance = new BankReport();
        }
        return instance;
    }

    public int getNumberOfClients(Bank bank) {
        return bank.getClients().size();

    }

    public int getAccountsNumber(Bank bank) {
        int result = 0;

        for (Client client : bank.getClients().values()) {
            result += client.getAccounts().size();
        }

        return result;
    }

    public Set<Client> getClientsSorted(Bank bank) {
        SortedSet<Client> sorted = new TreeSet<Client>();
        for (Client client : bank.getClients().values()) {
            sorted.add(client);
        }
        return sorted;
    }

    public double getBankCreditSum(Bank bank) {
        int result = 0;
        for (Client client : bank.getClients().values()) {
            for (Account account : client.getAccounts()) {
                if (account.getBalance() < 0) {
                    result += account.getBalance();
                }
            }
        }
        return result;
    }

    public Map<String, List<String>> getClientsByCity(Bank bank) {
        Map<String, List<String>> cityList = new HashMap<String, List<String>>();
        for (Client client : bank.getClients().values()) {
            if (!cityList.containsKey(client.getCity())) {
                List<String> set = new ArrayList<String>();
                set.add(client.getCity());
                cityList.put(client.getCity(), set);
            } else {
                List<String> set = cityList.get(client.getCity());
            }

        }
        return cityList;
    }

}
