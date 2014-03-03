package com.luxoft.bankapp.service.bank;

import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.ui.BankCommander;

/**
 * Created by aili on 23.02.14.
 */
public class DBSelectClientCommander implements Command {

    @Override
    public void execute() {
        Bank currentBank = BankCommander.getActiveBank();
        if (currentBank == null) {
            new DBSelectBankCommander().execute();
            currentBank = BankCommander.getActiveBank();
        }
        try {
            Client client = BankService.findClientByName(currentBank);
            client.setBank(BankCommander.getActiveBank());
            System.out.println("Client" + client.getFullName() + " was selected");
        } catch (ClientNotFoundException e) {
            e.getMessage();
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Select client by name");
    }
}

