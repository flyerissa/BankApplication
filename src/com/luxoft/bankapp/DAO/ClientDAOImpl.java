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
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientDAOImpl extends BaseDAO implements ClientDAO {

    private static Logger log = Logger.getLogger(ClientDAOImpl.class.getName());

    @Override
    public Client findClientByName(Bank bank, String name) throws SQLException {
        Client client = null;
        final String sql = "SELECT c.name, c.id, c.balance  FROM CLIENT as c JOIN BANK as b ON c.bank_id = b.id" +
                " WHERE bank_id = ? AND c.name = ?";
        log.log(Level.INFO, "Select client");

        Connection connection = getDataSource().getConnection();
        try (final PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, bank.getId());
            stmt.setString(2, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String clientName = rs.getString("name");
                log.log(Level.INFO, "Client was found " + clientName);
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
        log.log(Level.INFO, "Getting clients of the bank " + bank.getName());
        Connection connection = getDataSource().getConnection();
        try (final PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, bank.getId());
            ResultSet rs = stmt.executeQuery();//TODO: close??
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                Client client = new Client();
                client.setFullName(name);
                client.setId(id);
                list.add(client);
            }
            log.log(Level.INFO, "Clients were found" + list);
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
        final String clientSQL = "INSERT INTO CLIENT (NAME,BANK_ID, balance) VALUES (?,?,?)";
        final String accountSQL = "INSERT INTO ACCOUNT (client_id,type,balance,overdraft)VALUES (?,?,?,?)";
        log.log(Level.INFO, "Inserting client and accounts");
        try (Connection connection = getDataSource().getConnection();
             final PreparedStatement clientStmt = connection.prepareStatement(clientSQL);
             final PreparedStatement accountStmt = connection.prepareStatement(accountSQL)

        ) {
            clientStmt.setString(1, client.getFullName());
            clientStmt.setInt(2, client.getBank().getId());
            clientStmt.setDouble(3, client.getBalance());
            if (clientStmt.executeUpdate() == 0) {
                log.log(Level.INFO, "Cant insert client");
                throw new SQLException("Impossible to save Client in DB! Transaction is being rolled back!");

            }
            log.log(Level.INFO, "Client was inserted");
            ResultSet rs = clientStmt.getGeneratedKeys();//TODO close?
            if (rs == null || !rs.next()) {
                log.log(Level.INFO, "Cant get clients id");
                throw new SQLException("Impossible to save in DB! Cant get clientID!");
            }
            Integer clientId = rs.getInt(1);
            client.setId(clientId);
            for (Account a : client.getAccounts()) {
                accountStmt.setInt(1, client.getId());
                if (a instanceof CheckingAccount) {
                    accountStmt.setString(2, "checking");
                    accountStmt.setDouble(3, a.getBalance());
                    accountStmt.setDouble(4, ((CheckingAccount) a).getOverdraft());
                } else {
                    accountStmt.setString(2, "saving");
                    accountStmt.setDouble(3, a.getBalance());
                    accountStmt.setDouble(4, 0.00);
                }
            }
            if (accountStmt.executeUpdate() == 0) {
                log.log(Level.INFO, "Cant insert account");
                throw new SQLException("Impossible to save account in DB! Transaction is being rolled back!");
            }
        }
    }

    private void updateClient(Client client) throws SQLException {
        final String clientSQL = "UPDATE  CLIENT SET name = ?, bank_id = ?, gender = ?, phone = ?, city = ?, balance = ?," +
                "overdraft = ? WHERE id = ?";
        final String accountUpdateSQL = "UPDATE ACCOUNT SET  type  = ?, balance = ?, overdraft = ? where id = ?";
        final String accountNewSQL = "INSERT INTO ACCOUNT (client_id,type,balance,overdraft)VALUES (?,?,?,?)";
        log.log(Level.INFO, "Updating client");
        Connection connection = getDataSource().getConnection();
        try (
                final PreparedStatement clientStmt = connection.prepareStatement(clientSQL);
                final PreparedStatement accountStmt = connection.prepareStatement(accountUpdateSQL);
                final PreparedStatement accountNewStmt = connection.prepareStatement(accountNewSQL);
        ) {
            clientStmt.setString(1, client.getFullName());
            clientStmt.setInt(2, client.getBank().getId());
            clientStmt.setString(3, String.valueOf(client.getGender()));
            clientStmt.setString(4, client.getPhone());
            clientStmt.setString(5, client.getCity());
            clientStmt.setDouble(6, client.getBalance());
            clientStmt.setDouble(7, client.getOverdraft());
            clientStmt.setInt(8, client.getId());
            if (clientStmt.executeUpdate() == 0) {
                log.log(Level.INFO, "Cant update client");
                throw new SQLException("Impossible to update client in DB! Transaction is being rolled back!");
            }
            log.log(Level.INFO, "Client updated. Begin to update accounts");
            for (Account a : client.getAccounts()) {
                if (a.getId() == null) {
                    accountNewStmt.setInt(1, client.getId());
                    if (a instanceof CheckingAccount) {
                        accountNewStmt.setString(2, "checking");
                        accountNewStmt.setDouble(3, a.getBalance());
                        accountNewStmt.setDouble(4, ((CheckingAccount) a).getOverdraft());
                    } else {
                        accountNewStmt.setString(2, "saving");
                        accountNewStmt.setDouble(3, a.getBalance());
                        accountNewStmt.setDouble(4, 0.00);
                    }
                    if (accountNewStmt.executeUpdate() == 0) {
                        log.log(Level.INFO, "Cant insert accounts");
                        throw new SQLException("Impossible to update account in DB! Transaction is being rolled back!");
                    }
                } else {
                    if (a instanceof CheckingAccount) {
                        accountStmt.setString(1, "checking");
                        accountStmt.setDouble(2, a.getBalance());
                        accountStmt.setDouble(3, ((CheckingAccount) a).getOverdraft());
                    } else {
                        accountStmt.setString(1, "saving");
                        accountStmt.setDouble(2, a.getBalance());
                        accountStmt.setDouble(3, 0.00);
                    }
                    accountStmt.setInt(4, a.getId());
                    if (accountStmt.executeUpdate() == 0) {
                        log.log(Level.INFO, "Cant update accounts");
                        throw new SQLException("Impossible to update account in DB! Transaction is being rolled back!");
                    }
                    log.log(Level.INFO, "Accounts updated");
                }
            }
        }
    }

    @Override
    public void remove(Client client) throws SQLException {
        final String deleteAccountSQL = "DELETE FROM Account  WHERE client_id = ?";
        final String deleteClientSQL = "DELETE FROM CLIENT WHERE id = ?";
        log.log(Level.INFO, "Delete client and his accounts");
        Connection connection = getDataSource().getConnection();
        try (
                final PreparedStatement deleteAccountStmt = connection.prepareStatement(deleteAccountSQL);
                final PreparedStatement deleteClientStmt = connection.prepareStatement(deleteClientSQL)) {
            deleteAccountStmt.setInt(1, client.getId());
            if (deleteAccountStmt.executeUpdate() == 0) {
                log.log(Level.INFO, "Cant delete accounts");
                throw new SQLException("Impossible to delete account from DB! Transaction is being rolled back!");
            }
            log.log(Level.INFO, "Accounts were deleted");
            deleteClientStmt.setInt(1, client.getId());
            if (deleteClientStmt.executeUpdate() == 0) {
                log.log(Level.INFO, "Cant delete client");
                throw new SQLException("Impossible to delete client from DB! Transaction is being rolled back!");
            }
            log.log(Level.INFO, "Client was deleted");
        }
    }

    public Set<Account> getAllAccounts(Client client) throws SQLException {
        Account account;
        final String getAccounts = "SELECT id, type, balance, overdraft from ACCOUNT where client_id = ?";
        log.log(Level.INFO, "Getting client's accounts");
        Connection connection = getDataSource().getConnection();
        try (
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
            log.log(Level.INFO, "Accounts were found");
        }
        return client.getAccounts();
    }

    public static void main(String[] args) throws ClientExistsException {
        /*//Client client = new Client();
        Bank bank;
        try {
            bank = new BankDAOImpl().getBankByName("Bank");
            bank.setName("Test bank");
            Client client = new ClientDAOImpl().findClientByNameAsActive(bank, "Test inserting");
            //client.setFullName("Test inserting");
            Account account = client.addAccount("S", 3000, 0);
            client.addAccountToSet(account);
            //bank.addClient(client);
            //System.out.println(client.getBalance());
            new ClientDAOImpl().updateClient(client);

        } catch (SQLException e) {
            e.printStackTrace();
        }
*/

    }

}
