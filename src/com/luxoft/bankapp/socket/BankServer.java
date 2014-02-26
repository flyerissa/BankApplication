package com.luxoft.bankapp.socket;

import com.luxoft.bankapp.DAO.Implement.BankDAOImpl;
import com.luxoft.bankapp.DAO.Implement.ClientDAOImpl;
import com.luxoft.bankapp.commands.BankCommander;
import com.luxoft.bankapp.commands.DBSelectBankCommander;
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
            sendMessage("Connection successful");
            // 4. The two parts communicate via the input and output streams
            do {
                message = (String) in.readObject();
                if (message.equals("This is bankomat")) {
                    try {
                        sendMessage("Please enter name of the bank");
                        message = (String) in.readObject();
                        String bankname = message;
                        Bank bank = new BankDAOImpl().getBankByName(bankname);
                        BankCommander.setActiveBank(bank);
                        sendMessage("Please enter the name of client");
                        message = (String) in.readObject();
                        Client client = new ClientDAOImpl().findClientByName(bank, message);
                        BankCommander.setActiveClient(client);
                        sendMessage("Please choose the operation");
                        message = (String) in.readObject();
                        if (message.equals("Withdraw from client")) {

                            sendMessage("Please enter the sum to withdraw");
                            String sum = (String) in.readObject();
                            try {
                                BankService.withdrawAccount(BankCommander.getActiveClient(), Double.parseDouble(sum));
                                ClientDAOImpl clientDAO = new ClientDAOImpl();
                                try {
                                    clientDAO.save(BankCommander.getActiveClient());
                                    sendMessage(sum + " was withdrawen!");
                                    message = (String) in.readObject();
                                    if (message.equals("Display balance")) {
                                        Double balance = BankCommander.getActiveClient().getBalance();
                                        sendMessage("Balance is: " + balance);
                                    } else if (message.equals("bye")) {
                                        sendMessage("bye");
                                    }
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                            } catch (NotEnoughFundsException e) {
                                e.printStackTrace();
                            }

                        } else if (message.equals("Display balance")) {
                            Double balance = BankCommander.getActiveClient().getBalance();
                            sendMessage("Balance is: " + balance);
                            message = (String) in.readObject();
                        }

                    } catch (ClassNotFoundException classnot) {
                        System.err.println("Data received in unknown format");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else if (message.equals("This is remote office")) {
                    sendMessage("Please enter the name of the bank");
                    message = (String) in.readObject();
                    DBSelectBankCommander.selectBank(message);


                }


            } while (!message.equals("bye"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
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
