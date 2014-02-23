package com.luxoft.bankapp.domain.bank;

import com.luxoft.bankapp.exceptions.ClientExistsException;

import java.util.*;

//5th exercise
public class Bank {
    //private List<Client> clients = new ArrayList<Client>();
    //private Set<Client> clients = new HashSet<Client>();
    private Integer id;
    private Map<Integer, Client> clients = new HashMap<Integer, Client>();

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

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


    public static void main(String[] args) {
        Bank bank = new Bank();
        bank.setName("DDD");


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
