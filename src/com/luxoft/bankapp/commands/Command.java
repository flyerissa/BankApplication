package com.luxoft.bankapp.commands;


import com.luxoft.bankapp.exceptions.ClientExistsException;

public interface Command {
    void execute() throws ClientExistsException;

    void printCommandInfo();
}
