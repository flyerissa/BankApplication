package com.luxoft.bankapp.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.UnknownHostException;
import java.util.Scanner;

/**
 * Created by User on 26.02.14.
 */
public class BankRemoteOffice {
    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;
    static final String SERVER = "localhost";
    Scanner sc = new Scanner(System.in);

    void run() {
        try {
            // 1. creating a socket to connect to the server
            requestSocket = new Socket(SERVER, 2004);
            System.out.println("Connected to localhost in port 2004");
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
                } catch (UnknownHostException e) {
                    System.err.println("You are trying to connect to unknown host");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (UnknownHostException unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            // 4: Closing connection
            try {
                in.close();
                out.close();
                requestSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
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

    public static void main(final String args[]) {
        BankRemoteOffice office = new BankRemoteOffice();
        office.run();
    }


}
