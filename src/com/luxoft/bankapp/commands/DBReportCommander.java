package com.luxoft.bankapp.commands;

import com.luxoft.bankapp.DAO.Implement.BankDAOImpl;
import com.luxoft.bankapp.domain.bank.BankInfo;
import com.luxoft.bankapp.domain.bank.Client;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by aili on 24.02.14.
 */
public class DBReportCommander {
    public static void printReport(String name) {
        try {
            BankInfo bankInfo;
            BankDAOImpl bankDAO = new BankDAOImpl();
            bankInfo = bankDAO.getBankInfo();
            Map<String, List<Client>> map;
            map = bankInfo.getClientsByCity();
            System.out.println("Info for bank " + name + ": " + "Number of clients is: " + bankInfo.getNumberOfClients()
                    + ", total account sum is - " + bankInfo.getTotalAccountSum() +
                    ", list of clients, sorted by city: " + map);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
