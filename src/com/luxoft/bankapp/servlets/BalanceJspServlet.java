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
public class BalanceJspServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(LoginServlet2.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String clientName = (String) req.getSession().getAttribute("clientName");
        String bankName = (String) req.getSession().getAttribute("bankName");
        String accountId = (String) req.getSession().getAttribute("accountId");
        try {
            Bank bank = BankService.getInstance().selectBank(bankName);
            Client client = BankService.getInstance().findClientByNameAsActive(bank, clientName);
            Account account = BankService.getInstance().findAccountFromDB(client, Integer.parseInt(accountId));
            Double balance = account.getBalance();
            req.getSession().setAttribute("balance", balance);
            req.getRequestDispatcher("/jsp/secure/client/balance.jsp").forward(req, resp);
            //renderBalancePage(req, resp);

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
