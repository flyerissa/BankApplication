package com.luxoft.bankapp.commands;


public interface Command {
    void execute();

    void printCommandInfo();
}
