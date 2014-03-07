package com.luxoft.bankapp.DAO;

import com.luxoft.bankapp.domain.bank.*;
import com.luxoft.bankapp.exceptions.ClientExistsException;

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
             final PreparedStatement stmt = connection.prepareStatement(sql)) {
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
                client.setBalanceFromDB(client_balance);
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
        final String sql = "INSERT INTO CLIENT (NAME,BANK_ID, balance) VALUES (?,?,?)";
        final String sql2 = "INSERT INTO ACCOUNT (client_id,type,balance,overdraft)VALUES (?,?,?,?)";

        dataSource.getConnection().setAutoCommit(false);
        ResultSet rs;
        try (Connection connection = dataSource.getConnection();
             final PreparedStatement stmt = connection.prepareStatement(sql);
             final PreparedStatement statement = connection.prepareStatement(sql2)

        ) {
            stmt.setString(1, client.getFullName());
            stmt.setInt(2, client.getBank().getId());
            stmt.setDouble(3, client.getBalance());
            stmt.executeUpdate();
            //dataSource.getConnection().commit();
            if (stmt.executeUpdate() == 0) {
                dataSource.getConnection().rollback();
                throw new SQLException("Impossible to save Client in DB! Transaction is being rolled back!");
            } else {
                rs = stmt.getGeneratedKeys();

                if (rs != null && rs.next()) {
                    Integer clientId = rs.getInt(1);
                    client.setId(clientId);
                    //dataSource.getConnection().commit();
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
                        dataSource.getConnection().rollback();
                        throw new SQLException("Impossible to save account in DB! Transaction is being rolled back!");
                    } else {
                        dataSource.getConnection().commit();
                    }
                } else {
                    dataSource.getConnection().rollback();
                    throw new SQLException("Impossible to save in DB! Cant get clientID!");
                }
            }
        } finally {
            dataSource.getConnection().setAutoCommit(true);
        }
    }

    private void updateClient(Client client) throws SQLException {
        final String sql2 = "UPDATE  CLIENT SET name = ?, bank_id = ?, gender = ?, phone = ?, city = ?, balance = ?," +
                "overdraft = ? WHERE id = ?";
        final String sql1 = "UPDATE ACCOUNT SET client_id = ?, type  = ?, balance = ?, overdraft = ?";

        try (Connection connection = dataSource.getConnection();
             final PreparedStatement stmt = connection.prepareStatement(sql2);
             final PreparedStatement statement = connection.prepareStatement(sql1)
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
                throw new SQLException("Impossible to update client in DB! Transaction is being rolled back!");
            } else {
                dataSource.getConnection().commit();
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
                    throw new SQLException("Impossible to update account in DB! Transaction is being rolled back!");
                } else {
                    dataSource.getConnection().commit();
                }
            }

        } finally {
            dataSource.getConnection().setAutoCommit(true);
        }
    }

    @Override
    public void remove(Client client) throws SQLException {
        final String deleteAccountSQL = "DELETE FROM Account  WHERE client_id = ?";
        final String deleteClientSQL = "DELETE FROM CLIENT WHERE id = ?";
        try (Connection connection = dataSource.getConnection();
             final PreparedStatement deleteAccountStmt = connection.prepareStatement(deleteAccountSQL);
             final PreparedStatement deleteClientStmt = connection.prepareStatement(deleteClientSQL)) {
            dataSource.getConnection().setAutoCommit(false);
            deleteAccountStmt.setInt(1, client.getId());
            deleteAccountStmt.executeUpdate();
            if (deleteAccountStmt.executeUpdate() == 0) {
                connection.rollback();
                throw new SQLException("Impossible to delete account from DB! Transaction is being rolled back!");
            } else {
                connection.commit();
            }
            deleteClientStmt.setInt(1, client.getId());
            deleteClientStmt.executeUpdate();
            if (deleteClientStmt.executeUpdate() == 0) {
                connection.rollback();
                throw new SQLException("Impossible to delete client from DB! Transaction is being rolled back!");
            } else {
                connection.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();

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

    public static void main(String[] args) throws ClientExistsException {
        Client client = new Client();
        Bank bank;
        try {
            bank = new BankDAOImpl().getBankByName("Bank");
            bank.setName("Test bank");
            client.setFullName("Test inserting");
            Account account = client.addAccount("S", 5000, 0);
            client.addAccountToSet(account);
            bank.addClient(client);
            System.out.println(client.getBalance());
            new ClientDAOImpl().insertClient(client);

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
