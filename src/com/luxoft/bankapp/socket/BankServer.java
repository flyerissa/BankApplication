package com.luxoft.bankapp.socket;

import com.luxoft.bankapp.DAO.Implement.BankDAOImpl;
import com.luxoft.bankapp.DAO.Implement.ClientDAOImpl;
import com.luxoft.bankapp.commands.BankCommander;
import com.luxoft.bankapp.commands.DBSelectBankCommander;
import com.luxoft.bankapp.commands.DBSelectClientCommander;
import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;
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

            // 4. The two parts communicate via the input and output streams


            try {
                sendMessage("Connected. Please enter name of the bank");
                message = (String) in.readObject();
                Bank bank = new BankDAOImpl().getBankByName(message);
                DBSelectBankCommander.selectBank(bank.getName());

                sendMessage("Bank " + BankCommander.getActiveBank().getName() + " was chose.  Please enter the name of client");

                message = (String) in.readObject();
                Client client = new ClientDAOImpl().findClientByName(bank, message);
                client.setBank(bank);
                new ClientDAOImpl().save(client);
                DBSelectClientCommander.selectClient(client.getFullName());
                sendMessage("Client was found: " + BankCommander.getActiveClient().getFullName() + " Please choose the operation");
                message = (String) in.readObject();

                if (message.equalsIgnoreCase("withdraw")) {
                    sendMessage("Please enter the sum to withdraw");
                    message = (String) in.readObject();
                    try {
                        BankService.withdrawAccount(client, Double.parseDouble(message));
                        ClientDAOImpl clientDAO = new ClientDAOImpl();
                                try {
                                    clientDAO.save(client);
                                    sendMessage(message + " was withdrawen!");
                                    message = (String) in.readObject();

                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            } catch (NotEnoughFundsException e) {
                                e.printStackTrace();
                            }

                } else if (message.equalsIgnoreCase("balance")) {

                    sendMessage("Balance is: " + client.getBalance());

                } else if (message.equals("bye")) {
                    sendMessage(message);


                } else {
                    sendMessage("Please enter correct command!");
                }

            } catch (ClassNotFoundException classnot) {
                System.err.println("Data received in unknown format");
            } catch (SQLException e) {
                e.printStackTrace();
            }


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
