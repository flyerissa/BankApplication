package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.exceptions.ClientExistsException;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Created by User on 17.02.14.
 */
public class BankCommander {

    static Map<String, Command> mapCommands = new TreeMap<String, Command>();

    static {
        mapCommands.put("1", new AddClientCommand());
        mapCommands.put("2", new FindClientCommand());
        mapCommands.put("3", new GetAccountsCommand());
        mapCommands.put("4", new DepositCommand());
        mapCommands.put("5", new WithdrawCommand());
        mapCommands.put("6", new TransferCommand());
        mapCommands.put("7", new Command() {
            @Override
            public void execute() throws ClientExistsException {
                System.exit(0);
            }

            @Override
            public void printCommandInfo() {
                System.out.println("Exit");
            }
        });
    }


    public static void main(String args[]) {

        System.out.println("Enter number from 1 to 7 to choose command - add, find, getAccount, deposit, withdraw, transfer, exit");
        Scanner sc = new Scanner(System.in);

        String commandString = sc.nextLine();
        //int command = Integer.parseInt(commandString); // initialize command with commandString
        try {
            mapCommands.get(commandString).execute();
        } catch (ClientExistsException e) {
            e.printStackTrace();
        }
    }


    public static void registerCommand(String name, Command command) {
        mapCommands.put(name, command);
    }

    public static void removeCommand(String name) {
        mapCommands.remove(name);
    }

}

