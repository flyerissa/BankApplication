package com.luxoft.bankapp.servlets;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        if (clientName == null) {
            log.warning("Client not found!");
            throw new ServletException("No client specified.");
        }
        if (bankName == null) {
            log.warning("Bank not found!");
            throw new ServletException("No bank specified.");
        }
        req.getSession().setAttribute("clientName", clientName);
        req.getSession().setAttribute("bankName", bankName);

        log.info("Client " + clientName + " logged into ATM. Bank " + bankName + " was selected");

        resp.sendRedirect("/secure/menu.html");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        out.println("<html><body>");
        out.println("Logged as " + req.getSession().getAttribute("clientName"));
        out.println("</body></html>");
    }
}
