package com.luxoft.bankapp.servlets;

import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.exceptions.BankNotFoundException;
import com.luxoft.bankapp.service.bank.BankService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by aili on 15.03.14.
 */
public class SearchClientsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String bankName = (String) req.getSession().getAttribute("bankName");
        final String cityName = req.getParameter("city");
        final String clientName = req.getParameter("client");
        if (cityName == null || clientName == null) {
            throw new ServletException("All fields should be filled");
        }
        req.getSession().setAttribute("city", cityName);
        req.getSession().setAttribute("client", clientName);

        try {
            Bank bank = BankService.getInstance().selectBank(bankName);
            List<Client> clients = BankService.getInstance().findClientsByNameAndCity(bank, cityName, clientName);
            req.getSession().setAttribute("clients", clients);
            resp.sendRedirect("/jsp/secure/searchClients.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (BankNotFoundException e) {
            e.printStackTrace();
        }


    }
}
