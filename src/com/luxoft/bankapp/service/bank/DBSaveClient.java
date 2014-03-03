package com.luxoft.bankapp.service.bank;

import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.ui.BankCommander;

import java.sql.SQLException;

/**
 * Created by User on 27.02.14.
 */
public class DBSaveClient implements Command {
    @Override
    public void execute() {
        Client client = BankCommander.getActiveClient();
        try {
            BankService.saveOrUpdateClientToDB(client);
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Save or update client");
    }
}
