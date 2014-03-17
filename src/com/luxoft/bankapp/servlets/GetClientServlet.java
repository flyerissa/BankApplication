package com.luxoft.bankapp.servlets;

import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.service.bank.BankService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class GetClientServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String clientId = req.getParameter("clientId");
        try {
            Client client = BankService.getInstance().findClientById(Integer.parseInt(clientId));
            if (client == null) {
                throw new ServletException("There is no such client in DB!");
            }
            req.getSession().setAttribute("client", client);
            resp.sendRedirect("jsp/secure/office/addClient.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
