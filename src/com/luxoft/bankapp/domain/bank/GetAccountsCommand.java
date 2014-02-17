package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.service.bank.BankService;

/**
 * Created by User on 17.02.14.
 */
public class GetAccountsCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Enter name");
        BankService.getAccount(new FindClientCommand());
    }

    @Override
    public void printCommandInfo() {

    }
}
