package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.DAO.Implement.ClientDAOImpl;
import com.luxoft.bankapp.DAO.Implement.DBSelectClientCommander;
import com.luxoft.bankapp.service.bank.BankService;

import java.sql.SQLException;
import java.util.Scanner;

public class DepositCommand implements Command {
    @Override
    public void execute() {
        System.out.println("Please enter the name of the client");
        Scanner sc = new Scanner(System.in);
        String clientname = sc.nextLine();
        DBSelectClientCommander.selectClient(clientname);
        System.out.println("Enter sum to deposit");
        Scanner sc1 = new Scanner(System.in);
        String input = sc1.nextLine();
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
        System.out.println("Deposit");
    }
}
