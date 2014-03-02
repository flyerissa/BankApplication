package com.luxoft.bankapp.commands;

public class TransferCommand implements Command {
    @Override
    public void execute() {

        new DBSelectClientCommander().execute();
        new WithdrawCommand().execute();
        new DBSelectClientCommander().execute();
        new DepositCommand().execute();
        System.out.println("Transfer was successful!");
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Transfer");
    }
}
