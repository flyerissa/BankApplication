package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.service.bank.BankService;
import com.luxoft.bankapp.ui.BankCommander;


public class DBRemoveClientCommander implements Command {

    @Override
    public void execute() {
        Client client = BankCommander.getActiveClient();
        BankService.removeClientFromDB(client);
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Remove client");
    }
}
