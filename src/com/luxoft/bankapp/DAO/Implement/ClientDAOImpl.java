package com.luxoft.bankapp.DAO.Implement;

import com.luxoft.bankapp.DAO.ClientDAO;
import com.luxoft.bankapp.domain.bank.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 21.02.14.
 */
public class ClientDAOImpl implements ClientDAO {

    Connection connection;

    public ClientDAOImpl() {
        openConnection();
    }

    public Connection openConnection() {
        try {
            Class.forName("org.h2.Driver"); // this is driver for H2
            connection = DriverManager.getConnection("jdbc:h2:~/bankapp",
                    "sa", // login
                    "" // password
            );
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Client findClientByName(Bank bank, String name) throws SQLException {
        Client client = null;
        final String sql = "SELECT c.name, c.id, c.gender, c.balance as client_balance a.id as account_id, a.balance as a_balance, a.overdraft as a_overdraft" +
                ", a.type as acc_type b.name as bank_name" +
                " FROM CLIENT as C JOIN BANK as  b ON c.bank_id = b.id" +
                " JOIN ACCOUNT as a ON c.id = a.client_id" +
                " WHERE bank_id = ? AND name = ?";
        final PreparedStatement stmt = connection.prepareStatement(sql);
        try {
            stmt.setInt(1, bank.getId());
            stmt.setString(2, name);
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String clientName = rs.getString("name");
                String bank_name = rs.getString("bank_name");
                if (bank.getName().equals(bank_name) && name.equals(clientName)) {
                    int client_id = rs.getInt("id");
                    String gender = rs.getString("gender");
                    int account_id = rs.getInt("account_id");
                    double acc_balance = (double) rs.getInt("a_balance");
                    double overdraft = (double) rs.getInt("a_overdraft");
                    double client_balance = (double) rs.getInt("client_balance");
                    String acc_type = rs.getString("acc_type");

                    client = new Client(clientName, gender);
                    client.setId(client_id);
                    client.setBalance(client_balance);
                    Account account;
                    if (acc_type.equals("checking")) {
                        account = new CheckingAccount(acc_balance, overdraft);
                        account.setId(account_id);
                    } else {
                        account = new SavingAccount(acc_balance);
                        account.setId(account_id);
                    }

                    client.addAccountToSet(account);

                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return client;
    }

    @Override
    public List<Client> getAllClients(Bank bank) throws SQLException {
        List<Client> list = new ArrayList<>();
        final String sql = "SELECT c.id, c.name, c.gender, b.id as bank_id FROM CLIENT as c JOIN BANK as b" +
                "ON b.id = c.bank_id WHERE b.id = ?";
        final PreparedStatement stmt = connection.prepareStatement(sql);
        try {
            stmt.setInt(1, bank.getId());
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                int bank_id = rs.getInt("bank_id");
                if (bank.getId() == bank_id) {
                    Client client = new Client(name, gender);
                    list.add(client);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Method should insert new Client (if id==null)
     * or update client in database
     *
     * @param client
     */
    @Override
    public void save(Client client) throws SQLException {
        int id = client.getId();
        final String sql = "INSERT INTO CLIENT VALUES(?,?,?,?,?,?,?)";
        final PreparedStatement stmt = connection.prepareStatement(sql);
        if (id == 0) {
            stmt.setString(1, client.getFullName());


        }
    }

    @Override
    public void remove(Client client) {

    }
}
