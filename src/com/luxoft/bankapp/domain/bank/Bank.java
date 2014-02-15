package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.exceptions.ClientExistsException;

import java.util.ArrayList;
import java.util.List;

//3d exercise
public class Bank {
    private List<Client> clients = new ArrayList<Client>();
    private String name;
    private List<ClientRegistrationListener> listeners = new ArrayList<ClientRegistrationListener>();

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

    public List<Client> getClients() {
        return clients;
    }

    public void addClient(Client c) throws ClientExistsException {
        for (Client client : clients) {
            if (c.getName().equals(client.getName())) {
                throw new ClientExistsException();
            } else {
                clients.add(c);
                for (ClientRegistrationListener clientListener : listeners) {
                    clientListener.onClientAdded(c);
                }
            }
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
