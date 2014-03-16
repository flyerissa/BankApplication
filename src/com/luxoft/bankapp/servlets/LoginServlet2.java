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
 * Created by aili on 13.03.14.
 */
public class LoginServlet2 extends HttpServlet {
    private static Logger log = Logger.getLogger(LoginServlet2.class.getName());

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final String clientName = req.getParameter("client_name");
        final String bankName = req.getParameter("bank_name");
        final String accountId = req.getParameter("accountID");
        if (clientName == null) {
            log.warning("Client not found!");
            throw new ServletException("No client specified.");
        }
        if (bankName == null) {
            log.warning("Bank not found!");
            throw new ServletException("No bank specified.");
        }

        try {
            Bank bank = BankService.getInstance().selectBank(bankName);
            Client client = BankService.getInstance().findClientByNameAsActive(bank, clientName);
            Account account2 = BankService.getInstance().findAccountFromDB(client, Integer.parseInt(accountId));
            if (account2 == null) {
                log.warning("Account not found");
                throw new ServletException("No account specified");
            }

        } catch (BankNotFoundException e) {
            log.info(e.getMessage());
        } catch (ClientNotFoundException e) {
            log.info(e.getMessage());
        } catch (SQLException e) {
            log.info(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

        req.getSession().setAttribute("clientName", clientName);
        req.getSession().setAttribute("bankName", bankName);
        req.getSession().setAttribute("accountId", accountId);
        resp.sendRedirect("/jsp/secure/client/menu.jsp");
    }


}
