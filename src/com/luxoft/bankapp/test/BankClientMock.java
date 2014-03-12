package com.luxoft.bankapp.test;

import com.luxoft.bankapp.domain.bank.Account;
import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by User on 06.03.14.
 */
public class BankClientMock extends Thread implements Callable {
    private Client client;
    private Bank bank;
    private Account account;

    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;
    static final String SERVER = "localhost";

    private final static Logger log = Logger.getLogger(BankClientMock.class.getName());

    private Long timeWaiting;


    public void run() {
        synchronized (this) {
            try {
                requestSocket = new Socket(SERVER, 8080);
                System.out.println("Connected to localhost in port 8080");
                // 2. get Input and Output streams
                out = new ObjectOutputStream(requestSocket.getOutputStream());
                out.flush();
                in = new ObjectInputStream(requestSocket.getInputStream());
                message = (String) in.readObject();
                System.out.println("server>" + message);
                sendMessage("Bankomat");
                message = (String) in.readObject();
                System.out.println("server>" + message);
                sendMessage(bank.getName());
                message = (String) in.readObject();
                System.out.println("server>" + message);
                sendMessage(client.getFullName());
                message = (String) in.readObject();
                System.out.println("server>" + message);
                sendMessage(account.getId().toString());
                message = (String) in.readObject();
                System.out.println("server>" + message);
                sendMessage("withdraw");
                message = (String) in.readObject();
                System.out.println("server>" + message);
                sendMessage("10");
                message = (String) in.readObject();
                System.out.println("server>" + message);
                sendMessage("Bye");


            } catch (IOException e) {
                log.log(Level.SEVERE, e.getMessage(), e);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    out.close();
                    in.close();
                    requestSocket.close();
                } catch (IOException e) {
                    log.log(Level.SEVERE, e.getMessage(), e);
                }

            }
        }

    }

    public BankClientMock(Client client, Bank bank, Account account) {
        this.client = client;
        this.bank = bank;
        this.account = account;
    }

    void sendMessage(final String msg) {
        try {
            out.writeObject(msg);
            out.flush();

        } catch (IOException ioException) {
            log.log(Level.SEVERE, ioException.getMessage(), ioException);
        }
    }

    @Override
    public Object call() throws Exception {
        long start = System.currentTimeMillis();
        long end = 0;
        try {
            requestSocket = new Socket(SERVER, 8080);
            System.out.println("Connected to localhost in port 8080");
            // 2. get Input and Output streams
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());

            try {
                message = (String) in.readObject();
                System.out.println(message);
                sendMessage("Bankomat");
                message = (String) in.readObject();
                System.out.println(message);
                sendMessage(bank.getName());
                message = (String) in.readObject();
                System.out.println(message);
                sendMessage(client.getFullName());
                message = (String) in.readObject();
                System.out.println(message);
                sendMessage("3");
                message = (String) in.readObject();
                System.out.println(message);
                sendMessage("withdraw");
                message = (String) in.readObject();
                System.out.println(message);
                sendMessage("1");
                message = (String) in.readObject();
                System.out.println(message);
                sendMessage("Bye");
                end = System.currentTimeMillis();

            } catch (ClassNotFoundException e) {
                log.log(Level.SEVERE, e.getMessage(), e);
            }

        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage(), e);

        } finally {
            try {
                out.close();
                in.close();
                requestSocket.close();
            } catch (IOException e) {
                log.log(Level.SEVERE, e.getMessage(), e);
            }

        }
        timeWaiting = end - start;
        return timeWaiting;
    }
}
