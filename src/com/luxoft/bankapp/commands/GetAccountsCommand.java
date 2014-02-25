package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.service.bank.BankService;

public class GetAccountsCommand implements Command {
    @Override
    public void execute() {
        FindClientCommand findClientCommand = new FindClientCommand();
        findClientCommand.execute();
        Client currentClient = findClientCommand.currentClient;
        BankService.getAccount(currentClient);
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Get account info for client");
    }
}
