package com.luxoft.bankapp.DAO;

import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;

import java.util.List;

/**
 * Created by User on 21.02.14.
 */
public interface ClientDAO {
    /**
     * Return client by its name, initialize client accounts.
     *
     * @param bank
     * @param name
     * @return
     */
    Client findClientByName(Bank bank, String name);

    /**
     * Returns the list of all clients of the Bank
     * and their accounts
     *
     * @param bankId
     * @return
     */
    List<Client> getAllClients(Bank bankId);

    /**
     * Method should insert new Client (if id==null)
     * or update client in database
     *
     * @param client
     */
    void save(Client client);

    /**
     * Method removes client from Database
     *
     * @param client
     */
    void remove(Client client);
}
