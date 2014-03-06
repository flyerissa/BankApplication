package com.luxoft.bankapp.socket_multithread;

import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.exceptions.BankNotFoundException;
import com.luxoft.bankapp.service.bank.BankService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by User on 06.03.14.
 */
public class BankClientMock extends Thread {
    private Client client;
    private Bank bank;

    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;
    static final String SERVER = "localhost";

    public void run() {
        try {
            requestSocket = new Socket(SERVER, 8080);
            System.out.println("Connected to localhost in port 8080");
            // 2. get Input and Output streams
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());
            while (true) {
                try {
                    message = (String) in.readObject();
                    sendMessage("Bankomat");
                    message = (String) in.readObject();
                    sendMessage(bank.getName());
                    message = (String) in.readObject();
                    sendMessage(client.getFullName());
                    message = (String) in.readObject();
                    sendMessage("3");
                    message = (String) in.readObject();
                    sendMessage("withdraw");
                    message = (String) in.readObject();
                    sendMessage("1000");
                    message = (String) in.readObject();
                    sendMessage("Bye");

                    if (message.equals("bye")) break;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
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
}
