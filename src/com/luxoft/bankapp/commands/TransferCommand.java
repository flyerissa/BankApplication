package com.luxoft.bankapp.commands;

public class TransferCommand implements Command {
    @Override
    public void execute() {
        new DBSelectClientCommander();
        new WithdrawCommand();
        new DBSelectClientCommander();
        new DepositCommand();
    }

    @Override
    public void printCommandInfo() {
        System.out.println("Transfer");
    }
}
