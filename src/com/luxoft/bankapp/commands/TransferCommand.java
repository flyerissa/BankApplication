package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.DAO.Implement.ClientDAOImpl;
import com.luxoft.bankapp.DAO.Implement.DBSelectClientCommander;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.service.bank.BankService;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * Created by User on 17.02.14.
 */
public class TransferCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Please enter the name of the client");
        Scanner sc = new Scanner(System.in);
        String fromclient = sc.nextLine();
        DBSelectClientCommander.selectClient(fromclient);

        System.out.println("Enter sum to withdraw");
        Scanner sc1 = new Scanner(System.in);
        String input = sc1.nextLine();

        try {
            BankService.withdrawAccount(BankCommander.getActiveClient(), Double.parseDouble(input));
            ClientDAOImpl clientDAO = new ClientDAOImpl();
            try {
                clientDAO.save(BankCommander.getActiveClient());
                System.out.println(input + " was withdrawen!");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (NotEnoughFundsException e) {
            e.printStackTrace();
        }

        System.out.println("Please enter the name of the client to transfer");
        Scanner sc2 = new Scanner(System.in);
        String toclient = sc2.nextLine();
        DBSelectClientCommander.selectClient(toclient);

        BankService.depositAccount(BankCommander.getActiveClient(), Double.parseDouble(input));
        ClientDAOImpl clientDAO = new ClientDAOImpl();
        try {
            clientDAO.save(BankCommander.getActiveClient());
            System.out.println(input + " was deposited!");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void printCommandInfo() {
        System.out.println("Transfer");
    }
}
