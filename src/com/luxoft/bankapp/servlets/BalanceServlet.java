package com.luxoft.bankapp.servlets;

import com.luxoft.bankapp.domain.bank.Account;
import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.exceptions.BankNotFoundException;
import com.luxoft.bankapp.exceptions.ClientNotFoundException;
import com.luxoft.bankapp.service.bank.BankService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Created by User on 14.03.14.
 */
public class BalanceServlet extends HttpServlet {

    private static Logger log = Logger.getLogger(LoginServlet2.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String clientName = (String) req.getSession().getAttribute("clientName");
        String bankName = (String) req.getSession().getAttribute("bankName");
        String account = (String) req.getSession().getAttribute("accountId");
        try {
            Bank bank = BankService.getInstance().selectBank(bankName);
            Client client = BankService.getInstance().findClientByNameAsActive(bank, clientName);
            Account account1 = BankService.getInstance().findAccountFromDB(client, Integer.parseInt(account));
            Double balance = account1.getBalance();
            final String accountBalance = req.getParameter("bal");


            log.info("Client " + clientName + ". For account " + account1.getId() + " Balance is " + balance);
        } catch (BankNotFoundException e) {
            log.info(e.getMessage());
        } catch (ClientNotFoundException e) {
            log.info(e.getMessage());
        } catch (SQLException e) {
            log.info(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
