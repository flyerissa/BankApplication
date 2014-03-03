package com.luxoft.bankapp.ui;

import com.luxoft.bankapp.commands.Command;
import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.service.bank.*;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Created by User on 17.02.14.
 */
public class BankCommander {

    public static Bank activeBank;
    public static Client activeClient;

    public static void setActiveBank(Bank activeBank) {
        BankCommander.activeBank = activeBank;
    }

    public static void setActiveClient(Client activeClient) {
        BankCommander.activeClient = activeClient;
    }

    public static Bank getActiveBank() {
        return activeBank;
    }

    public static Client getActiveClient() {
        return activeClient;
    }

    static Map<String, Command> mapCommands = new TreeMap<String, Command>();

    static {
        mapCommands.put("1", new DBSelectBankCommander());
        mapCommands.put("2", new DBSelectClientCommander());
        mapCommands.put("3", new GetClientAccountsCommand());
        mapCommands.put("4", new DepositCommand());
        mapCommands.put("5", new WithdrawCommand());
        mapCommands.put("6", new TransferCommand());
        mapCommands.put("7", new Command() {
            @Override
            public void execute() {
                System.exit(0);
            }

            @Override
            public void printCommandInfo() {
                System.out.println("Exit");
            }
        });
    }

    public static void main(String args[]) {


        System.out.println("1 - select bank \n 2 - select client \n 3 - get accounts \n 4 - deposit  \n 5 - withdraw  \n 6 - transfer  \n 7 - exit");
        String commandString;
        do {
            Scanner sc = new Scanner(System.in);

            commandString = sc.nextLine();
            //int command = Integer.parseInt(commandString); // initialize command with commandString

            mapCommands.get(commandString).execute();
        } while (!commandString.equals("7"));

    }

    public static void registerCommand(String name, Command command) {
        mapCommands.put(name, command);
    }

    public static void removeCommand(String name) {
        mapCommands.remove(name);
    }
}

