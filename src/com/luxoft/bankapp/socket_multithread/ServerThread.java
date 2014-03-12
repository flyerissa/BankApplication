package com.luxoft.bankapp.socket_multithread;

import com.luxoft.bankapp.DAO.TransactionManager;
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
import java.net.Socket;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.concurrent.Callable;
import java.util.logging.*;

/**
 * Created by User on 06.03.14.
 */
public class ServerThread implements Runnable {
    //ServerSocket providerSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String message;
    private Socket connection = null;
    private static Logger log = Logger.getLogger(ServerThread.class.getName());
    private long start;
    private long end;

    static {
        try {
            log.addHandler(new FileHandler("serverThread.log"));
            log.setFilter(new Filter() {
                String message1 = null;

                @Override
                public boolean isLoggable(LogRecord record) {
                    String newMesg = record.getMessage();
                    if (message1 != null && message1.equals(newMesg)) {
                        return false;
                    } else {
                        message1 = newMesg;
                        return true;
                    }
                }
            });
        } catch (IOException e) {
            log.severe(e.getMessage());
        }
    }

    public ServerThread(Socket client) {
        connection = client;
    }

    @Override
    public void run() {
        try {
            System.out.println("Connection received from "
                    + connection.getInetAddress().getHostName());
            // 3. get Input and Output streams
            out = new ObjectOutputStream(connection.getOutputStream());
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());

            // 4. The two parts communicate via the input and output streams

            try {
                start = Calendar.getInstance().getTimeInMillis();
                log.info("Connected at " + Calendar.getInstance().getTime());
                BankServerThreaded.getCounter().decrementAndGet();

                //if (message.equalsIgnoreCase("Bankomat")) {
                BankScenario bankScenario = new BankScenario();
                bankScenario.runScenario();//FIXME: transactional
                // } else if (message.equalsIgnoreCase("Office")) {
                //   selectBankForInfo();


            } catch (ClassNotFoundException classnot) {
                log.log(Level.SEVERE, classnot.getMessage(), classnot);
            } catch (BankNotFoundException e) {
                log.log(Level.WARNING, e.getMessage(), e);
                sendMessage(e.getMessage());
            } catch (ClientNotFoundException e) {
                log.log(Level.WARNING, e.getMessage(), e);
                sendMessage(e.getMessage());
            } catch (NotEnoughFundsException e) {
                log.log(Level.WARNING, e.getMessage(), e);
                sendMessage(e.getMessage());
            } catch (SQLException e) {
                log.log(Level.SEVERE, e.getMessage(), e);
            } catch (BankInfoException e) {
                log.log(Level.WARNING, e.getMessage(), e);
                sendMessage(e.getMessage());
            } catch (Exception e) {
                log.log(Level.SEVERE, e.getMessage(), e);
            }

        } catch (IOException ioException) {
            log.log(Level.SEVERE, ioException.getMessage(), ioException);
        } finally {
            // 4: Closing connection
            try {
                end = Calendar.getInstance().getTimeInMillis();
                log.info(connection + " disconnected at " + Calendar.getInstance().getTime());
                long last = (end - start) / 1000;
                log.info("Connected time " + last + " seconds");
                in.close();
                out.close();
                connection.close();
            } catch (IOException ioException) {
                log.log(Level.SEVERE, ioException.getMessage(), ioException);
            }
        }
    }

    void sendMessage(final String msg) {
        try {
            out.writeObject(msg);
            out.flush();

        } catch (IOException ioException) {
            log.log(Level.SEVERE, ioException.getMessage(), ioException);
        }
    }

    private void selectBankForInfo() throws IOException, ClassNotFoundException, SQLException {
        if (BankCommander.getActiveBank() == null) {
            sendMessage("Hello Office. Please enter name of the bank");
            message = (String) in.readObject();
            Bank current;
            try {
                current = BankService.getInstance().findBankByNameAndSetActive(message);
                BankInfo bankInfo = BankService.getInstance().getBankInfo(message);
                sendMessage("Bank " + current.getName() + " was chosen." +
                        " Number of clients is: " + bankInfo.getNumberOfClients() +
                        ", total account sum is - " + bankInfo.getTotalAccountSum() +
                        ", list of clients sorted by city: " + bankInfo.getClientsByCity() +
                        "\n Please enter the name of client to recieve the data: ");
                message = (String) in.readObject();
                Client client = BankService.getInstance().findClientByNameAsActive(current, message);
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

    private class BankScenario {
        private Bank bank;
        private Client client;
        private Account currentAccount;
        private BankService instance;

        private void selectBank() throws Exception {
            sendMessage("Connected. Please introduce yourself");
            message = (String) in.readObject();
            sendMessage("Hello Bankomat. Please enter name of the bank");
            message = (String) in.readObject();
            instance = BankService.getInstance();
            bank = instance.selectBank(message);
            if (bank != null) {
                sendMessage("Bank " + bank.getName() + " was chosen. Please enter name of the client");
                message = (String) in.readObject();
            } else {
                throw new BankNotFoundException("There is no such bank! Please retry!");//TODO: request new bank or exit
            }
        }

        private void selectClient() throws Exception {

            client = instance.selectClient(bank, message);
            if (client != null) {
                //client.setBank(bank);
                instance.getAllAccounts(client);
                sendMessage("Client" + client.getFullName() + " was selected. Please choose active account by its id: " + client.getAccounts());
                message = (String) in.readObject();
            } else {
                throw new ClientNotFoundException("There is no such client in DB!");//TODO: retry or exit
            }
        }

        private void processAccount() throws Exception {
            currentAccount = instance.findAccountFromDB(client, Integer.parseInt(message));
            if (currentAccount == null) {
                sendMessage("Incorrect account id or account is not found. please retry!");
                return;
            }
            client.setActiveAccount(currentAccount);
            sendMessage("Account " + currentAccount.getId() + " was selected. Please choose the operation - withdraw or balance ");
            message = (String) in.readObject();
            if (message.equalsIgnoreCase("withdraw")) {
                sendMessage("Please enter the sum to withdraw");
                message = (String) in.readObject();
                instance.withdrawAccount(client, Double.parseDouble(message));
                instance.saveOrUpdateClientToDB(client);
                sendMessage(message + " was withdrawen! Current balance is " + currentAccount.getBalance()
                        + ". Enter bye to exit");
                message = (String) in.readObject();
                if (message.equalsIgnoreCase("bye")) {
                    sendMessage(message);
                }
            } else if (message.equalsIgnoreCase("balance")) {
                sendMessage("Balance is: " + currentAccount.getBalance());
            } else {
                sendMessage("Please enter correct command!");
            }
        }

        public void runScenario() throws Exception {
            TransactionManager tm = TransactionManager.getInstance();
            tm.doInTransaction(new Callable<Void>() {
                @Override
                public Void call() throws Exception {
                    selectBank();
                    selectClient();
                    processAccount();
                    return null;
                }
            });
        }

    }
}