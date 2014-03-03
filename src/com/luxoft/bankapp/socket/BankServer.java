package com.luxoft.bankapp.socket;

import com.luxoft.bankapp.domain.bank.Account;
import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.BankInfo;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.exceptions.BankInfoException;
import com.luxoft.bankapp.exceptions.BankNotFoundException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.service.bank.BankService;
import com.luxoft.bankapp.ui.BankCommander;

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
                sendMessage("Connected. Please introduce youself");
                message = (String) in.readObject();
                if (message.equalsIgnoreCase("Bankomat")) {
                    selectBank();
                } else if (message.equalsIgnoreCase("Office")) {
                    selectBankForInfo();
                }

            } catch (ClassNotFoundException classnot) {
                System.err.println("Data received in unknown format");
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

    private void selectBank() throws IOException, ClassNotFoundException {
        if (BankCommander.getActiveBank() == null) {
            sendMessage("Hello Bankomat. Please enter name of the bank");
            message = (String) in.readObject();
            Bank current;
            try {
                BankService instance = BankService.getInstance();
                current = instance.findBankByName(message);
                sendMessage("Bank " + current.getName() + " was chose. Please enter name of the client");
                message = (String) in.readObject();
                try {
                    Client client = instance.findClientByName(current, message);
                    client.setBank(BankCommander.getActiveBank());
                    instance.getAllAccounts(client);
                    sendMessage("Client" + client.getFullName() + " was selected. Please choose active account by its id: " + client.getAccounts());
                    message = (String) in.readObject();
                    for (Account a : client.getAccounts()) {
                        if (a.getId().equals(Integer.parseInt(message))) {
                            BankCommander.getActiveClient().setActiveAccount(a);
                            sendMessage("Account " + a.getId() + " was selected. Please choose the operation - withdraw or balance");
                            message = (String) in.readObject();

                            if (message.equalsIgnoreCase("withdraw")) {
                                sendMessage("Please enter the sum to withdraw");
                                message = (String) in.readObject();
                                try {
                                    instance.withdrawAccount(client, Double.parseDouble(message));
                                    instance.saveOrUpdateClientToDB(client);
                                    sendMessage(message + " was withdrawen! Current balance is " + client.getActiveAccount().getBalance()
                                            + ". Enter bye to exit");
                                    message = (String) in.readObject();

                                } catch (NotEnoughFundsException e) {
                                    e.printStackTrace();
                                } catch (SQLException e) {
                                    sendMessage(e.getMessage());
                                }

                            } else if (message.equalsIgnoreCase("balance")) {

                                sendMessage("Balance is: " + client.getBalance());

                            } else if (message.equals("bye")) {
                                sendMessage(message);


                            } else {
                                sendMessage("Please enter correct command!");
                            }

                        } else {
                            sendMessage("Incorrect id, please retry!");
                            return;
                        }
                    }

                } catch (ClientNotFoundException e) {
                    sendMessage(e.getMessage());
                }
            } catch (BankNotFoundException e) {
                e.getMessage();
            }
        }
    }



    private void selectBankForInfo() throws IOException, ClassNotFoundException {
        if (BankCommander.getActiveBank() == null) {
            sendMessage("Hello Office. Please enter name of the bank");
            message = (String) in.readObject();
            Bank current;
            try {
                current = BankService.getInstance().findBankByName(message);
                BankInfo bankInfo = BankService.getInstance().getBankInfo(message);
                sendMessage("Bank " + current.getName() + " was chosen." +
                        " Number of clients is: " + bankInfo.getNumberOfClients() +
                        ", total account sum is - " + bankInfo.getTotalAccountSum() +
                        ", list of clients sorted by city: " + bankInfo.getClientsByCity() +
                        "\n Please enter the name of client to recieve the data: ");
                message = (String) in.readObject();
                Client client = BankService.getInstance().findClientByName(current, message);
                sendMessage("Info for client: " + client.getFullName() + ". Accounts: " + client.getAccounts() +
                        ". Total balance: " + client.getBalance());
            } catch (BankNotFoundException e) {
                e.getMessage();
            } catch (BankInfoException e) {
                e.printStackTrace();
            } catch (ClientNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

}
