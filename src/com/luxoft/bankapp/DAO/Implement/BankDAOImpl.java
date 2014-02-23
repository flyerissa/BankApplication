package com.luxoft.bankapp.DAO.Implement;

import com.luxoft.bankapp.DAO.BankDao;
import com.luxoft.bankapp.domain.bank.Bank;

import java.sql.*;

/**
 * Created by User on 21.02.14.
 */
public class BankDAOImpl implements BankDao {
    Connection connection;

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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return bank;

    }

    public static void main(String[] args) {
        try {
            Bank bank = new BankDAOImpl().getBankByName("NN");
            System.out.println(bank.getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
