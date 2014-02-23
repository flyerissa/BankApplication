package com.luxoft.bankapp.DAO.Implement;

import com.luxoft.bankapp.commands.BankCommander;
import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by aili on 23.02.14.
 */
public class DBSelectClientCommander {
    public static void selectClient(String name) {
        ClientDAOImpl clientDAO = new ClientDAOImpl();
        Bank currentBank = BankCommander.getActiveBank();
        if (!currentBank.equals(null)) {
            try {
                Client client = clientDAO.findClientByName(currentBank, name);
                if (!client.equals(null)) {
                    BankCommander.activeClient = client;
                    System.out.println("Client" + client.getFullName() + " was selected");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Please enter the name of the bank");
            Scanner sc = new Scanner(System.in);
            String bankname = sc.nextLine();
            DBSelectBankCommander.selectBank(bankname);
            return;
        }
    }
}
