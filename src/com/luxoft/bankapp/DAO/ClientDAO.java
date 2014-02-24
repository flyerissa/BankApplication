package com.luxoft.bankapp.DAO;

import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;

import java.sql.SQLException;
import java.util.List;

public interface ClientDAO {
    /**
     * Return client by its name, initialize client accounts.
     *
     * @param bank
     * @param name
     * @return
     */
    Client findClientByName(Bank bank, String name) throws SQLException;

    /**
     * Returns the list of all clients of the Bank
     * and their accounts
     *
     * @param bankId
     * @return
     */
    List<Client> getAllClients(Bank bank) throws SQLException;

    /**
     * Method should insert new Client (if id==null)
     * or update client in database
     *
     * @param client
     */
    void save(Client client) throws SQLException;

    /**
     * Method removes client from Database
     *
     * @param client
     */
    void remove(Client client) throws SQLException;
}
