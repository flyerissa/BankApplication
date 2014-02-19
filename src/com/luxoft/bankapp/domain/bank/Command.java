package com.luxoft.bankapp.domain.bank;


import com.luxoft.bankapp.exceptions.ClientExistsException;

public interface Command {
    void execute() throws ClientExistsException;

    void printCommandInfo();
}
