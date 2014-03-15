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
 * Created by aili on 15.03.14.
 */
public class DepositServlet extends HttpServlet {

    private static Logger log = Logger.getLogger(LoginServlet2.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String sum = req.getParameter("deposit_sum");
        if (sum == null) {
            throw new ServletException("Please enter the sum to deposit!");
        }
        req.getSession().setAttribute("deposit", sum);

        String clientName = (String) req.getSession().getAttribute("clientName");
        String bankName = (String) req.getSession().getAttribute("bankName");
        String accountId = (String) req.getSession().getAttribute("accountId");
        String depositSum = (String) req.getSession().getAttribute("deposit");
        try {
            Bank bank = BankService.getInstance().selectBank(bankName);
            Client client = BankService.getInstance().findClientByNameAsActive(bank, clientName);
            Account account = BankService.getInstance().findAccountFromDB(client, Integer.parseInt(accountId));
            client.setActiveAccount(account);
            BankService.getInstance().depositAccount(client, Double.parseDouble(depositSum));
            BankService.getInstance().saveOrUpdateClientToDB(client);

            resp.sendRedirect("/balance2");


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
