package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.service.bank.BankService;

/**
 * Created by aili on 23.02.14.
 */
public class DBSelectClientCommander implements Command {

    @Override
    public void execute() {
        Bank currentBank = BankCommander.getActiveBank();
        if (currentBank != null) {
            try {
                Client client = BankService.findClientByName(currentBank);
                System.out.println("Client" + client.getFullName() + " was selected");
            } catch (ClientNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            new DBSelectBankCommander().execute();
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Select client by name");
    }
}

