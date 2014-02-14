package com.luxoft.bankapp.service.bank;

import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.domain.bank.ClientExistsException;

import java.util.List;


public class BankService {
    public static void addClient(Bank bank, Client client) throws ClientExistsException {
        int size = bank.getClients().size();
        String nextName = client.getName();
        List<Client> list = bank.getClients();

        if (size > 0) {
            for (Client client1 : list) {
                if (client1.getName().equals(nextName)) {
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
}
