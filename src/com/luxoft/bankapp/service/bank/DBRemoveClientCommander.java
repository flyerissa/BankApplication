package com.luxoft.bankapp.service.bank;

import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.ui.BankCommander;


public class DBRemoveClientCommander implements Command {

    @Override
    public void execute() {
        Client client = BankCommander.getActiveClient();
        BankService.getInstance().removeClientFromDB(client);
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Remove client");
    }
}
