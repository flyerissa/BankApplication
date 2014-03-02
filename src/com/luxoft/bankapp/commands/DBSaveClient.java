package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.service.bank.BankService;
import com.luxoft.bankapp.ui.BankCommander;

/**
 * Created by User on 27.02.14.
 */
public class DBSaveClient implements Command {
    @Override
    public void execute() {
        Client client = BankCommander.getActiveClient();
        BankService.saveOrUpdateClientToDB(client);
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Save or update client");
    }
}
