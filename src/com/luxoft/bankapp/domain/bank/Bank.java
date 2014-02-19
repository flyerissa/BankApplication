package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.exceptions.ClientExistsException;

import java.util.*;

//5th exercise
public class Bank {
    //private List<Client> clients = new ArrayList<Client>();
    private Set<Client> clients = new HashSet<Client>();

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

    public Set<Client> getClients() {
        return Collections.unmodifiableSet(clients);
    }

    public void addClient(Client c) throws ClientExistsException {
        if (clients.size() > 0) {
            for (Client client : clients) {
                if (c.equals(client)) {
                    throw new ClientExistsException();
                } else {
                    clients.add(c);
                    for (ClientRegistrationListener clientListener : listeners) {
                        clientListener.onClientAdded(c);
                    }
                }
            }

        } else {
            clients.add(c);
            for (ClientRegistrationListener clientListener : listeners) {
                clientListener.onClientAdded(c);
            }
        }


    }

    public static void main(String[] args) {
        Bank bank = new Bank();
        bank.setName("DDD");
        Client cl1 = new Client("HH JJ", Gender.MALE);
        Client cl2 = new Client("LL JJ", Gender.MALE);
        Client cl3 = new Client("HH JJ", Gender.MALE);
        try {
            bank.addClient(cl1);
            bank.addClient(cl2);
            bank.addClient(cl3);
        } catch (ClientExistsException e) {
            System.out.println("Client alrady exists!");
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
