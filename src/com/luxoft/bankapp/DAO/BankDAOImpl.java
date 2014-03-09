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

//FIXME
public class BankDAOImpl extends BaseDAO implements BankDao {

    @Override
    public Bank getBankByName(String name) throws SQLException {
        Bank bank = null;
        final String sql = "SELECT id, name FROM BANK WHERE name = ?";
        Connection connection = getDataSource().getConnection();
        try (
                final PreparedStatement stmt = connection.prepareStatement(sql)
        ) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String bankName = rs.getString("NAME");
                int id = rs.getInt("id");
                bank = new Bank();
                bank.setId(id);
                bank.setName(bankName);
            } else {
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
        Connection connection = getDataSource().getConnection();
        try (
                final PreparedStatement numberStmt = connection.prepareStatement(numberClientsSQL);
                final PreparedStatement balanceStmt = connection.prepareStatement(balanceSQL);
                final PreparedStatement cityStmt = connection.prepareStatement(orderClientsSQL)
        ) {
            numberStmt.setInt(1, current.getId());
            ResultSet rs = numberStmt.executeQuery();
            if (!rs.next()) {
                throw new SQLException("Impossible get number of clients!");
            }
            bankInfo = new BankInfo();
            bankInfo.setNumberOfClients(rs.getInt("number_of_clients"));
            balanceStmt.setInt(1, current.getId());
            ResultSet rs2 = balanceStmt.executeQuery();
            if (!rs2.next()) {
                throw new SQLException("Impossible get balance!");
            }
            bankInfo.setTotalAccountSum(rs2.getDouble("total"));

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
        }
        return bankInfo;
    }
}
