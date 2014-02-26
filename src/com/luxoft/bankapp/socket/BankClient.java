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
public class BankClient {
    Socket requestSocket;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;
    static final String SERVER = "localhost";

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
            do {
                try {
                    sendMessage("This is bankomat");
                    message = (String) in.readObject();
                    System.out.println("Please enter the name of the bank");
                    Scanner scan = new Scanner(System.in);
                    String bankname = scan.nextLine();
                    sendMessage(bankname);

                    System.out.println("Please enter the name of the client");
                    Scanner sc = new Scanner(System.in);
                    String clientname = sc.nextLine();
                    sendMessage(clientname);
                    System.out.println("Do you want to withdraw or review the balance?");
                    Scanner sc2 = new Scanner(System.in);
                    String choice = sc2.nextLine();
                    if (choice.equals("withdraw")) {
                        sendMessage("Withdraw from client");
                        message = (String) in.readObject();

                        System.out.println("Enter sum to withdraw");
                        Scanner sc1 = new Scanner(System.in);
                        String input = sc1.nextLine();
                        sendMessage(input);

                    }


                    if (choice.equals("balance")) {
                        sendMessage("Display balance");
                        message = (String) in.readObject();
                        System.out.println("server " + message);
                        sendMessage("bye");

                    }

                } catch (ClassNotFoundException classNot) {
                    System.err.println("data received in unknown format");
                }
            } while (!message.equals("bye"));
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
        BankClient client = new BankClient();
        client.run();
    }
}
