package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.exceptions.ClientExistsException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by User on 17.02.14.
 */
public class BankCommander {

    static Map<String, Command> mapCommands = new HashMap<String, Command>();

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
        while (true) {
            for (int i = 0; i < mapCommands.size(); i++) { // show menu
                System.out.print(i + ") ");
                mapCommands.get(i).printCommandInfo();
            }
            Scanner sc = new Scanner(System.in);

            String commandString = sc.nextLine();
            int command = Integer.parseInt(commandString); // initialize command with commandString
            try {
                mapCommands.get(command).execute();
            } catch (ClientExistsException e) {
                e.printStackTrace();
            }
        }
    }
}

