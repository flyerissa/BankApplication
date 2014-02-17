package com.luxoft.bankapp.domain.bank;

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
            String commandString = System.console().readLine();
            int command = 0; // initialize command with commandString
            commands[command].execute();
        }
    }
}

