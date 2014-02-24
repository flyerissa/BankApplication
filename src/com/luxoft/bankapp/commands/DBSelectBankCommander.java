package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.DAO.Implement.BankDAOImpl;
import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.exceptions.ClientExistsException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by aili on 23.02.14.
 */
public class DBSelectBankCommander {
    public static void selectBank(String name) {
        BankDAOImpl bankDAO = new BankDAOImpl();
        try {
            Bank bank = bankDAO.getBankByName(name);
            if (bank != null) {
                BankCommander.setActiveBank(bank);
                final String sql = "SELECT c.id as client FROM CLIENT as c JOIN BANK as b ON c.bank_id = b.id " +
                        "WHERE b.id = ?";
                final PreparedStatement stmt = bankDAO.connection.prepareStatement(sql);
                try {
                    stmt.setInt(1, bank.getId());
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        Integer client_id = rs.getInt("client");
                        Client client = new Client();
                        client.setId(client_id);
                        try {
                            bank.addClient(client);
                        } catch (ClientExistsException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    stmt.close();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            bankDAO.closeConnection();
        }
    }

}
