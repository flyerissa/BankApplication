package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.exceptions.ClientExistsException;

import java.util.Scanner;

/**
 * Created by User on 17.02.14.
 */
public class BankCommander {
    static Command[] commands = {
            new FindClientCommand(), // 1
            new GetAccountsCommand(), // 2
            new DepositCommand(),
            new WithdrawCommand(),
            new TransferCommand(),
            new AddClientCommand(),
            new Command() { // 7 - Exit Command
                public void execute() {
                    System.exit(0);
                }

                public void printCommandInfo() {
                    System.out.println("Exit");
                }
            }
    };

    public static void main(String args[]) {
        while (true) {
            for (int i = 0; i < commands.length; i++) { // show menu
                System.out.print(i + ") ");
                commands[i].printCommandInfo();
            }
            Scanner sc = new Scanner(System.in);

            String commandString = sc.nextLine();
            int command = Integer.parseInt(commandString); // initialize command with commandString
            try {
                commands[command].execute();
            } catch (ClientExistsException e) {
                e.printStackTrace();
            }
        }
    }
}

