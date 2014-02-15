package com.luxoft.bankapp.service.bank;

import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.exceptions.ClientExistsException;

import java.util.List;

//3d exercise
public class BankService {
    public static void addClient(Bank bank, Client client) throws ClientExistsException {
        int size = bank.getClients().size();
        String nextName = client.getName();
        List<Client> list = bank.getClients();

        if (size > 0) {
            for (Client existingClient : list) {
                if (existingClient.getName().equals(nextName)) {
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
