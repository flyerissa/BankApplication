package com.luxoft.bankapp.service.bank;


import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;

import java.util.Set;

public class BankReport {

    public int getNumberOfClients(Bank bank) {
        return bank.getClients().size();

    }

    public int getAccountsNumber(Bank bank) {
        int result = 0;
        for (Client client : bank.getClients()) {
            result += client.getAccounts().size();
        }
        return result;
    }

    public Set<Client> getClientsSorted(Bank bank) {

    }


}
