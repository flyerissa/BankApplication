package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.annotations.NoDB;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.ui.BankCommander;

import java.io.Serializable;
import java.util.*;

//5th exercise
public class Bank implements Serializable {
    @NoDB
    private Integer id;
    private Map<Integer, Client> clients = new HashMap<Integer, Client>();
    private String name;
    @NoDB
    private List<ClientRegistrationListener> listeners = new ArrayList<ClientRegistrationListener>();
    @NoDB
    private Map<String, Client> clientsByName = new HashMap<String, Client>();

    public void parseFeed(Map<String, String> feed) throws ClientExistsException {
        String name = feed.get("name"); // client name
        String gender = feed.get("gender");
        Client client = clientsByName.get(name);
        if (client == null) { // if no client then create it
            client = new Client();
            client.setFullName(name);
            if (gender.equals("f")) client.setGender(Gender.FEMALE);
            else if (gender.equals("m")) client.setGender(Gender.MALE);
            addClient(client);
            BankCommander.setActiveClient(client);
            clientsByName.put(name, client);
        }
        /**
         * This method should read all info
         * about the client from the feed map
         */
        client.parseFeed(feed);
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }


    public Bank() {
        listeners.add(new EmailNotificationListener());
        listeners.add(new PrintClientListener());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<Integer, Client> getClients() {
        return Collections.unmodifiableMap(clients);
    }

    public void addClient(Client c) throws ClientExistsException {
        clients.put(c.getId(), c);
        c.setBank(this);
        for (ClientRegistrationListener clientListener : listeners) {
            clientListener.onClientAdded(c);
        }
    }

    private class EmailNotificationListener implements ClientRegistrationListener {
        @Override
        public void onClientAdded(Client c) {
            System.out.println("Notification email for client " + c + " to be sent.");
        }
    }

    private class PrintClientListener implements ClientRegistrationListener {
        @Override
        public void onClientAdded(Client c) {
            System.out.println(c);
        }
    }

}
