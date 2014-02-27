package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.service.bank.BankService;

import java.util.List;

/**
 * Created by User on 27.02.14.
 */
public class DBSelectClientsOfBank implements Command {
    @Override
    public void execute() {
        new DBSelectBankCommander().execute();
        Bank current = BankCommander.getActiveBank();
        try {
            List<Client> listClients = BankService.getAllClients(current);
            System.out.println(listClients);
        } catch (ClientNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Show all clients of the bank");
    }
}
