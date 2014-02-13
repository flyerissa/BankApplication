package com.luxoft.bankapp.domain.bank;

import java.util.ArrayList;
import java.util.List;
import com.luxoft.bankapp.service.bank.ClientRegistrationListener;

public class Bank {
    private List<Client> clients = new ArrayList<Client>();
    private String name;
    private ClientRegistrationListener[] listeners;

    public Bank() {
        listeners = new ClientRegistrationListener[]{
                new EmailNotificationListener(), new PrintClientListener()
        };
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

    public void addClient(Client c) {
        clients.add(c);
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
