package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.service.bank.BankService;

/**
 * Created by User on 17.02.14.
 */
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
