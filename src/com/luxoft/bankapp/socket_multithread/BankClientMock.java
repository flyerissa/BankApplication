package com.luxoft.bankapp.socket_multithread;

import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.exceptions.BankNotFoundException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.service.bank.BankService;

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
                    sendMessage("10");
                    message = (String) in.readObject();
                    System.out.println(message);
                    sendMessage("Bye");

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    out.close();
                    in.close();
                    requestSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }

    public BankClientMock(Client client) {
        this.client = client;
        try {
            bank = BankService.getInstance().findBankByName("Bank");
        } catch (BankNotFoundException e) {
            e.printStackTrace();
        }
    }

    void sendMessage(final String msg) {
        try {
            out.writeObject(msg);
            out.flush();
            System.out.println("client>" + msg);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void main(String[] args) throws BankNotFoundException, ClientNotFoundException {
        BankClientMock clientMock = new BankClientMock(BankService.getInstance().findClientByNameAsActive(BankService.getInstance().findBankByName("Bank"), "JJ KK"));
        clientMock.run();
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
                sendMessage("10");
                message = (String) in.readObject();
                System.out.println(message);
                sendMessage("Bye");
                end = System.currentTimeMillis();

            } catch (ClassNotFoundException e) {
                log.log(Level.INFO, e.getMessage(), e);
            }

        } catch (IOException e) {
            log.log(Level.INFO, e.getMessage(), e);

        } finally {
            try {
                out.close();
                in.close();
                requestSocket.close();
            } catch (IOException e) {
                log.log(Level.INFO, e.getMessage(), e);
            }

        }
        timeWaiting = end - start;
        return timeWaiting;
    }
}
