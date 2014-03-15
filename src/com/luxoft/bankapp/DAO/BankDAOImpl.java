package com.luxoft.bankapp.DAO;

import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.BankInfo;
import com.luxoft.bankapp.domain.bank.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

//FIXME
public class BankDAOImpl extends BaseDAO implements BankDao {
    private static Logger log = Logger.getLogger(BankDAOImpl.class.getName());

    @Override
    public Bank getBankByName(String name) throws SQLException {
        Bank bank = null;
        final String sql = "SELECT id, name FROM BANK WHERE name = ?";
        log.log(Level.INFO, "Getting bank");
        Connection connection = getDataSource().getConnection();
        try (
                final PreparedStatement stmt = connection.prepareStatement(sql)
        ) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String bankName = rs.getString("name");
                int id = rs.getInt("id");
                bank = new Bank();
                bank.setId(id);
                bank.setName(bankName);
                log.log(Level.INFO, "Bank " + bankName + " was found");
            } else {
                log.log(Level.INFO, "Bank " + name + " was not found");
                System.out.println("There is no such bank in DB!");
            }
        }
        return bank;
    }

    @Override
    public BankInfo getBankInfo(String name) throws SQLException {
        BankInfo bankInfo = null;
        Bank current = getBankByName(name);
        final String numberClientsSQL = "select  count(c.id) as number_of_clients From Client as c " +
                " Join Bank  on c.bank_id = ? ";
        final String balanceSQL = "Select sum(c.balance) as total from client as c  where bank_id = ?";
        final String orderClientsSQL = "select city, name, id from client  where bank_id = ? order by city";
        log.log(Level.INFO, "Getting bankInfo for " + name);
        Connection connection = getDataSource().getConnection();
        try (
                final PreparedStatement numberStmt = connection.prepareStatement(numberClientsSQL);
                final PreparedStatement balanceStmt = connection.prepareStatement(balanceSQL);
                final PreparedStatement cityStmt = connection.prepareStatement(orderClientsSQL)
        ) {
            numberStmt.setInt(1, current.getId());
            ResultSet rs = numberStmt.executeQuery();
            if (!rs.next()) {
                log.log(Level.INFO, "Cant get number of clients");
                throw new SQLException("Impossible get number of clients!");
            }
            log.log(Level.INFO, "Number of clients was found");
            bankInfo = new BankInfo();
            bankInfo.setNumberOfClients(rs.getInt("number_of_clients"));
            balanceStmt.setInt(1, current.getId());
            ResultSet rs2 = balanceStmt.executeQuery();
            if (!rs2.next()) {
                log.log(Level.INFO, "Cant get balance");
                throw new SQLException("Impossible get balance!");
            }
            bankInfo.setTotalAccountSum(rs2.getDouble("total"));
            log.log(Level.INFO, "Total balance was found");

            cityStmt.setInt(1, current.getId());
            ResultSet rs3 = cityStmt.executeQuery();
            List<Client> clients = new ArrayList<>();
            while (rs3.next()) {
                Client client = new Client();
                client.setId(rs3.getInt("id"));
                client.setFullName(rs3.getString("name"));
                client.setCity(rs3.getString("city"));
                clients.add(client);
            }

            Map<String, List<Client>> sortedbycity = new TreeMap<String, List<Client>>();
            for (Client client : clients) {
                if (!sortedbycity.containsKey(client.getCity())) {
                    List<Client> set = new ArrayList<>();
                    set.add(client);
                    sortedbycity.put(client.getCity(), set);
                } else {
                    List<Client> list = sortedbycity.get(client.getCity());
                    list.add(client);
                }
            }
            bankInfo.setClientsByCity(sortedbycity);
            log.log(Level.INFO, "Sorted list by city was generated");
        }
        return bankInfo;
    }

    public List<Client> findClientsByNameAndCity(Bank bank, String name, String city) throws SQLException {
        List<Client> listOfClients = null;
        Bank current = getBankByName(name);
        final String findClients = "SELECT id, name, balance FROM CLIENT WHERE bank_id = ? AND city = ? AND name = ? ";
        log.info("Getting clients..");
        Connection connection = getDataSource().getConnection();
        try (final PreparedStatement search = connection.prepareStatement(findClients)) {
            search.setInt(1, current.getId());
            search.setString(2, city);
            search.setString(3, name);
            ResultSet result = search.executeQuery();
            if (!result.next()) {
                throw new SQLException("There are no clients with  name " + name + ", from " + city + " in bank" + current.getName());
            }
            log.info("Clients were found");
            listOfClients = new ArrayList<>();
            while (result.next()) {
                Client client = new Client();
                client.setId(result.getInt("id"));
                client.setFullName(result.getString("name"));
                client.setCity(result.getString("city"));
                client.setBalanceFromDB(result.getDouble("balance"));
                listOfClients.add(client);
            }
        }
        return listOfClients;

    }
}
