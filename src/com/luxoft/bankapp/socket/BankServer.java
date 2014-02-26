package com.luxoft.bankapp.socket;

import com.luxoft.bankapp.DAO.Implement.ClientDAOImpl;
import com.luxoft.bankapp.commands.BankCommander;
import com.luxoft.bankapp.commands.DBSelectClientCommander;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.service.bank.BankService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

/**
 * Created by User on 26.02.14.
 */
public class BankServer {
    ServerSocket providerSocket;
    Socket connection = null;
    ObjectOutputStream out;
    ObjectInputStream in;
    String message;

    void run() {
        try {
            // 1. creating a server socket
            providerSocket = new ServerSocket(2004, 10);
            // 2. Wait for connection
            System.out.println("Waiting for connection");
            connection = providerSocket.accept();
            System.out.println("Connection received from "
                    + connection.getInetAddress().getHostName());
            // 3. get Input and Output streams
            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());
            sendMessage("Connection successful");
            // 4. The two parts communicate via the input and output streams
            do {
                try {
                    sendMessage("Please enter the name of client");
                    message = (String) in.readObject();
                    DBSelectClientCommander.selectClient(message);
                    message = (String) in.readObject();
                    if (message.equals("Withdraw from client")) {

                        sendMessage("Please enter the sum to withdraw");
                        Double sum = (Double) in.readObject();
                        try {
                            BankService.withdrawAccount(BankCommander.getActiveClient(), sum);
                            ClientDAOImpl clientDAO = new ClientDAOImpl();
                            try {
                                clientDAO.save(BankCommander.getActiveClient());
                                sendMessage(sum + " was withdrawen!");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        } catch (NotEnoughFundsException e) {
                            e.printStackTrace();
                        }
                        message = (String) in.readObject();
                        if (message.equals("Display balance")) {
                            Double balance = BankCommander.getActiveClient().getBalance();
                            sendMessage("Balance is: " + balance);
                        } else if (message.equals("bye")) {
                            sendMessage("bye");
                        }
                    } else if (message.equals("Display balance")) {
                        Double balance = BankCommander.getActiveClient().getBalance();
                        sendMessage("Balance is: " + balance);
                    }

                } catch (ClassNotFoundException classnot) {
                    System.err.println("Data received in unknown format");
                }
            } while (!message.equals("bye"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            // 4: Closing connection
            try {
                in.close();
                out.close();
                providerSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    void sendMessage(final String msg) {
        try {
            out.writeObject(msg);
            out.flush();
            System.out.println("server>" + msg);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void main(final String args[]) {
        BankServer server = new BankServer();
        while (true) {
            server.run();
        }
    }
}
