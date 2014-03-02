package com.luxoft.bankapp.DAO;

import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.BankInfo;
import com.luxoft.bankapp.domain.bank.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class BankDAOImpl implements BankDao {
    private DataSource dataSource;

    public BankDAOImpl() {
        this.dataSource = new DataSource();
    }

    @Override
    public Bank getBankByName(String name) throws SQLException {
        Bank bank = null;
        final String sql = "SELECT id, name FROM BANK WHERE name = ?";
        try (Connection connection = dataSource.getConnection();
             final PreparedStatement stmt = connection.prepareStatement(sql);
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
    public BankInfo getBankInfo() throws SQLException {
        BankInfo bankInfo = null;
        System.out.println("Enter name of the bank!");
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        Bank current = getBankByName(name);
        final String sql = "select  count(c.id) as number_of_clients From Client as c " +
                " Join Bank  on c.bank_id = ? ";
        try (Connection connection = dataSource.getConnection();
             final PreparedStatement stmt = connection.prepareStatement(sql);
        ) {
            stmt.setInt(1, current.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs != null && rs.next()) {
                bankInfo = new BankInfo();
                bankInfo.setNumberOfClients(rs.getInt("number_of_clients"));

            }
        }
        final String sql2 = "Select sum(c.balance) as total from client as c  where bank_id = ?";
        try (Connection connection = dataSource.getConnection();
             final PreparedStatement stmt2 = connection.prepareStatement(sql2);) {
            stmt2.setInt(1, current.getId());
            ResultSet rs2 = stmt2.executeQuery();
            if (rs2 != null && rs2.next()) {
                bankInfo.setTotalAccountSum(rs2.getDouble("total"));
            }

        }
        final String sql3 = "select city, name, id from client  where bank_id = ? order by city";
        try (Connection connection = dataSource.getConnection();
             final PreparedStatement stmt3 = connection.prepareStatement(sql3);) {
            stmt3.setInt(1, current.getId());
            ResultSet rs3 = stmt3.executeQuery();
            List<Client> clients = new ArrayList<>();
            while (rs3 != null && rs3.next()) {
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

    public BankInfo getInfoByBankName(String name) throws SQLException {
        Bank current = null;
        BankInfo bankInfo = null;
        current = getBankByName(name);
        Integer id = current.getId();
        final String sql = "select  count(c.id) as number_of_clients From Client as c " +
                " Join Bank  on c.bank_id = ? ";
        try (Connection connection = dataSource.getConnection();
             final PreparedStatement stmt = connection.prepareStatement(sql);) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                bankInfo = new BankInfo();
                bankInfo.setNumberOfClients(rs.getInt("number_of_clients"));
            }

        }

        final String sql2 = "Select sum(c.balance) as total from client as c  where bank_id = ?";
        try (Connection connection = dataSource.getConnection();
             final PreparedStatement stmt2 = connection.prepareStatement(sql2);) {
            stmt2.setInt(1, current.getId());
            ResultSet rs2 = stmt2.executeQuery();
            if (rs2 != null && rs2.next()) {
                bankInfo.setTotalAccountSum(rs2.getDouble("total"));
            }
        }
        final String sql3 = "select city, name, id from client  where bank_id = ? order by city";
        try (Connection connection = dataSource.getConnection();
             final PreparedStatement stmt3 = connection.prepareStatement(sql3);) {
            stmt3.setInt(1, current.getId());
            ResultSet rs3 = stmt3.executeQuery();
            List<Client> clients = new ArrayList<>();
            while (rs3 != null && rs3.next()) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bankInfo;
    }


    public static void main(String[] args) {
        try {
            BankInfo info = new BankDAOImpl().getInfoByBankName("Bank");
            System.out.println(info.getNumberOfClients());
            System.out.println(info.getClientsByCity());
            System.out.println(info.getTotalAccountSum());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
