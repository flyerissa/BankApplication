package com.luxoft.bankapp.DAO;

import com.luxoft.bankapp.domain.bank.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by User on 21.02.14.
 */
public class ClientDAOImpl implements ClientDAO {
    private DataSource dataSource;

    public ClientDAOImpl() {
        this.dataSource = new DataSource();
    }

    @Override
    public Client findClientByName(Bank bank, String name) throws SQLException {
        Client client = null;
        final String sql = "SELECT c.name, c.id, c.balance  FROM CLIENT as c JOIN BANK as b ON c.bank_id = b.id" +
                " WHERE bank_id = ? AND c.name = ?";
        try (Connection connection = dataSource.getConnection();
             final PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setInt(1, bank.getId());
            stmt.setString(2, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String clientName = rs.getString("name");
                int client_id = rs.getInt("id");
                Double client_balance = rs.getDouble("balance");
                client = new Client();
                client.setId(client_id);
                client.setFullName(clientName);
                client.setBank(bank);
                client.setBalance(client_balance);
            }
        }
        return client;
    }

    @Override
    public List<Client> getAllClients(Bank bank) throws SQLException {
        List<Client> list = new ArrayList<>();
        final String sql = "SELECT c.id, c.name FROM CLIENT as c JOIN BANK as b" +
                " ON b.id = c.bank_id WHERE b.id = ?";
        try (Connection connection = dataSource.getConnection();
             final PreparedStatement stmt = connection.prepareStatement(sql);) {
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
        }
        return list;
    }


    @Override
    public void save(Client client) throws SQLException {
        if (client.getId() != null) {
            updateClient(client);
        } else {
            insertClient(client);
        }
    }

    private void insertClient(Client client) throws SQLException {
        final String sql = "INSERT INTO CLIENT (NAME,BANK_ID) VALUES (?,?)";
        final String sql2 = "INSERT INTO ACCOUNT (client_id,type,balance,overdraft)VALUES (?,?,?,?)";
        dataSource.getConnection().setAutoCommit(false);
        try (Connection connection = dataSource.getConnection();
             final PreparedStatement stmt = connection.prepareStatement(sql);
             final PreparedStatement statement = connection.prepareStatement(sql2);
        ) {
            stmt.setString(1, client.getFullName());
            stmt.setInt(2, client.getBank().getId());
            stmt.executeUpdate();
            if (stmt.executeUpdate() == 0) {
                dataSource.getConnection().rollback();
                throw new SQLException("Impossible to save in DB! Transaction is being rolled back!");
            }
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs != null && rs.next()) {
                Integer c_id = rs.getInt(1);
                client.setId(c_id);
                for (Account a : client.getAccounts()) {
                    statement.setInt(1, c_id);
                    if (a instanceof CheckingAccount) {
                        statement.setString(2, "checking");
                        statement.setDouble(3, a.getBalance());
                        statement.setDouble(4, ((CheckingAccount) a).getOverdraft());
                    } else {
                        statement.setString(2, "saving");
                        statement.setDouble(3, a.getBalance());
                        statement.setDouble(4, 0.00);
                    }
                }
                statement.executeUpdate();
                if (statement.executeUpdate() == 0) {
                    dataSource.getConnection().rollback();
                    throw new SQLException("Impossible to save in DB! Transaction is being rolled back!");
                }
            }

        } finally {
            dataSource.getConnection().setAutoCommit(true);
        }
    }

    private void updateClient(Client client) throws SQLException {
        final String sql = "UPDATE  CLIENT SET name = ?, bank_id = ?, gender = ?, phone = ?, city = ?, balance = ?," +
                "overdraft = ? WHERE id = ?";

        final String sql2 = "UPDATE ACCOUNT SET client_id = ?, type  = ?, balance = ?, overdraft = ?";

        try (Connection connection = dataSource.getConnection();
             final PreparedStatement stmt = connection.prepareStatement(sql);
             final PreparedStatement statement = connection.prepareStatement(sql2);
        ) {
            connection.setAutoCommit(false);
            stmt.setString(1, client.getFullName());
            stmt.setInt(2, client.getBank().getId());
            stmt.setString(3, String.valueOf(client.getGender()));
            stmt.setString(4, client.getPhone());
            stmt.setString(5, client.getCity());
            stmt.setDouble(6, client.getBalance());
            stmt.setDouble(7, client.getOverdraft());
            stmt.setInt(8, client.getId());
            int result = stmt.executeUpdate();
            if (result == 0) {
                connection.rollback();
                throw new SQLException("Impossible to save in DB! Transaction is being rolled back!");
            }
            for (Account a : client.getAccounts()) {
                statement.setInt(1, client.getId());
                if (a instanceof CheckingAccount) {
                    statement.setString(2, "checking");
                    statement.setDouble(3, a.getBalance());
                    statement.setDouble(4, ((CheckingAccount) a).getOverdraft());
                } else {
                    statement.setString(2, "saving");
                    statement.setDouble(3, a.getBalance());
                    statement.setDouble(4, 0.00);
                }
            }
            statement.executeUpdate();
            if (statement.executeUpdate() == 0) {
                connection.rollback();
                throw new SQLException("Impossible to save in DB! Transaction is being rolled back!");
            }

        } finally {
            dataSource.getConnection().setAutoCommit(true);
        }
    }

    @Override
    public void remove(Client client) throws SQLException {
        final String sql = "DELETE FROM Account  WHERE client_id = ?";
        final String sql2 = "DELETE FROM CLIENT WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(sql);
             final PreparedStatement statement1 = connection.prepareStatement(sql2);) {
            dataSource.getConnection().setAutoCommit(false);
            statement.setInt(1, client.getId());
            statement.executeUpdate();
            statement1.setInt(1, client.getId());
            statement1.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            if (dataSource.getConnection() != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    dataSource.getConnection().rollback();
                } catch (SQLException excep) {
                    e.printStackTrace();
                }
            }
        } finally {
            dataSource.getConnection().setAutoCommit(true);
        }

    }

    public Set<Account> getAllAccounts(Client client) throws SQLException {
        Account account;
        final String getAccounts = "SELECT id, type, balance, overdraft from ACCOUNT where client_id = ?";
        try (Connection connection = dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement(getAccounts)) {
            statement.setInt(1, client.getId());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int acc_id = rs.getInt("id");
                Double balance = rs.getDouble("balance");
                Double overdraft = rs.getDouble("overdraft");
                String type = rs.getString("type");
                if (type.equals("checking")) {
                    account = new CheckingAccount(balance, overdraft);
                    account.setId(acc_id);
                    client.addAccountToSet(account);
                } else {
                    account = new SavingAccount(balance);
                    account.setId(acc_id);
                    client.addAccountToSet(account);
                }
            }
        }
        return client.getAccounts();
    }

}
