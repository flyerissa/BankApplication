package com.luxoft.bankapp.DAO;

import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.BankInfo;
import com.luxoft.bankapp.domain.bank.Client;

import java.sql.*;
import java.util.*;

public class BankDAOImpl implements BankDao {
    public Connection connection;

    public BankDAOImpl() {
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
    public Bank getBankByName(String name) throws SQLException {
        Bank bank = null;
        final String sql = "SELECT id, name FROM BANK WHERE name = ?";
        final PreparedStatement stmt = connection.prepareStatement(sql);
        try {
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

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                stmt.close();
                closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
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
        openConnection();
        final PreparedStatement stmt = connection.prepareStatement(sql);

        try {
            stmt.setInt(1, current.getId());
            ResultSet rs = stmt.executeQuery();
            if (rs != null && rs.next()) {
                bankInfo = new BankInfo();
                bankInfo.setNumberOfClients(rs.getInt("number_of_clients"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            stmt.close();
            closeConnection();
        }

        final String sql2 = "Select sum(c.balance) as total from client as c  where bank_id = ?";
        openConnection();
        final PreparedStatement stmt2 = connection.prepareStatement(sql2);
        try {
            stmt2.setInt(1, current.getId());
            ResultSet rs2 = stmt2.executeQuery();
            if (rs2 != null && rs2.next()) {
                bankInfo.setTotalAccountSum(rs2.getDouble("total"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            stmt2.close();
            closeConnection();
        }

        final String sql3 = "select city, name, id from client  where bank_id = ? order by city";
        openConnection();
        final PreparedStatement stmt3 = connection.prepareStatement(sql3);

        try {
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

    public BankInfo getInfoByBankName(String name) throws SQLException {
        Bank current = null;
        BankInfo bankInfo = null;
        current = getBankByName(name);
        Integer id = current.getId();
        final String sql = "select  count(c.id) as number_of_clients From Client as c " +
                " Join Bank  on c.bank_id = ? ";
        openConnection();
        final PreparedStatement stmt = connection.prepareStatement(sql);

        try {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                bankInfo = new BankInfo();
                bankInfo.setNumberOfClients(rs.getInt("number_of_clients"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            stmt.close();
            closeConnection();
        }

        final String sql2 = "Select sum(c.balance) as total from client as c  where bank_id = ?";
        openConnection();
        final PreparedStatement stmt2 = connection.prepareStatement(sql2);
        try {
            stmt2.setInt(1, current.getId());
            ResultSet rs2 = stmt2.executeQuery();
            if (rs2 != null && rs2.next()) {
                bankInfo.setTotalAccountSum(rs2.getDouble("total"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            stmt2.close();
            closeConnection();
        }

        final String sql3 = "select city, name, id from client  where bank_id = ? order by city";
        openConnection();
        final PreparedStatement stmt3 = connection.prepareStatement(sql3);

        try {
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
        } finally {
            stmt3.close();
            closeConnection();
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
