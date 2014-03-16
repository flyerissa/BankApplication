package com.luxoft.bankapp.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by aili on 15.03.14.
 */
public class BankLoginServlet extends HttpServlet {

    private static Logger log = Logger.getLogger(LoginServlet2.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String bankName = req.getParameter("bank_name");
        if (bankName == null) {
            log.warning("Please enter bankName");
            throw new ServletException("Bank name should be entered");
        }

        req.getSession().setAttribute("bankName", bankName);
        resp.sendRedirect("/bankinfo");

    }
}
