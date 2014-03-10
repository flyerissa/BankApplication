package com.luxoft.bankapp.socket_multithread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by User on 06.03.14.
 */
public class BankClient2 {
    private Socket requestSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String message;
    private static final String SERVER = "localhost";
    private Scanner sc = new Scanner(System.in);
    private static Logger log = Logger.getLogger(BankClient2.class.getName());


    void run() {
        try {
            // 1. creating a socket to connect to the server
            requestSocket = new Socket(SERVER, 8080);
            System.out.println("Connected to localhost in port 2004");
            log.log(Level.INFO, String.valueOf(System.currentTimeMillis()));
            // 2. get Input and Output streams
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(requestSocket.getInputStream());
            // 3: Communicating with the server
            while (true) {
                try {
                    message = (String) in.readObject();
                    System.out.println("server > " + message);
                    message = sc.nextLine();
                    sendMessage(message);
                    if (message.equals("bye")) break;
                    log.log(Level.INFO, String.valueOf(System.currentTimeMillis()));
                } catch (UnknownHostException e) {
                    log.log(Level.INFO, e.getMessage(), e);
                } catch (ClassNotFoundException e) {
                    log.log(Level.INFO, e.getMessage(), e);
                }
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
    }


    void sendMessage(final String msg) {
        try {
            out.writeObject(msg);
            out.flush();
            System.out.println("client>" + msg);
        } catch (IOException ioException) {
            log.log(Level.INFO, ioException.getMessage(), ioException);
        }
    }

    public static void main(final String args[]) {
        BankClient2 client = new BankClient2();
        client.run();
    }


}
