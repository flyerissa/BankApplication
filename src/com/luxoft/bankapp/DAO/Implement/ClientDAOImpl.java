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
        final String sql = "SELECT c.name, c.id, a.id as account_id, a.balance as a_balance, a.overdraft as a_overdraft" +
                ", a.type as acc_type, b.name as bank_name" +
                " FROM CLIENT as c JOIN BANK as b ON c.bank_id = b.id" +
                " JOIN ACCOUNT as a ON c.id = a.client_id" +
                " WHERE bank_id = ? AND c.name = ?";
        final PreparedStatement stmt = connection.prepareStatement(sql);
        try {
            stmt.setInt(1, bank.getId());
            stmt.setString(2, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String clientName = rs.getString("name");
                String bank_name = rs.getString("bank_name");
                int client_id = rs.getInt("id");
                int account_id = rs.getInt("account_id");
                double acc_balance = (double) rs.getInt("a_balance");
                double overdraft = (double) rs.getInt("a_overdraft");
                String acc_type = rs.getString("acc_type");
                client = new Client();
                client.setId(client_id);
                client.setFullName(clientName);
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
        final String sql = "SELECT c.id, c.name FROM CLIENT as c JOIN BANK as b" +
                " ON b.id = c.bank_id WHERE b.id = ?";
        final PreparedStatement stmt = connection.prepareStatement(sql);
        try {
            stmt.setInt(1, bank.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Client client = new Client();
                client.setFullName(name);
                client.setId(id);
                list.add(client);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    @Override
    public void save(Client client) throws SQLException {
        if (client.getId() != null) {
            final String sql = "UPDATE  CLIENT SET name = ?, bank_id = ?, gender = ?, phone = ?, city = ?, balance = ?," +
                    "overdraft = ? WHERE id = ?";
            final PreparedStatement stmt = connection.prepareStatement(sql);
            try {
                stmt.setString(1, client.getFullName());
                stmt.setInt(2, client.getBank().getId());
                stmt.setString(3, client.getGender().toString());
                stmt.setString(4, client.getPhone());
                stmt.setString(5, client.getCity());
                stmt.setDouble(6, client.getBalance());
                stmt.setDouble(7, client.getOverdraft());
                stmt.setInt(8, client.getId());
                stmt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            final String sql = "INSERT INTO CLIENT (NAME,BANK_ID) VALUES (?,?)";
            final PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            try {
                stmt.setString(1, client.getFullName());
                stmt.setInt(2, client.getBank().getId());
                stmt.executeUpdate();
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs != null && rs.next()) {
                    Integer c_id = rs.getInt(1);
                    client.setId(c_id);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void remove(Client client) {
        final String sql = "DELETE FROM Account  WHERE client_id = ?";
        final String sql2 = "DELETE FROM CLIENT WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, client.getId());
            statement.executeUpdate();
            PreparedStatement statement1 = connection.prepareStatement(sql2);
            statement1.setInt(1, client.getId());
            statement1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        try {
            Bank bank = new BankDAOImpl().getBankByName("Bank");
            Client client = new ClientDAOImpl().findClientByName(bank, "JJ KK");
            new ClientDAOImpl().remove(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
