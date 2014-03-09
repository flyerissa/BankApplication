package com.luxoft.bankapp.commands;


public interface Command {
    void execute() throws Exception;

    void printCommandInfo();
}
