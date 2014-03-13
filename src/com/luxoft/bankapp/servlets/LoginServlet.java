package com.luxoft.bankapp.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by aili on 13.03.14.
 */
public class LoginServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(LoginServlet.class.getName());

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        final String clientName = req.getParameter("clientName");
        final String bankName = req.getParameter("bankName");
        if (bankName == null) {
            log.warning("Bank not found!");
            throw new ServletException("No bank specified.");
        } else if (clientName == null) {
            log.warning("Client not found!");
            throw new ServletException("No client specified.");
        }
        req.getSession().setAttribute("clientName", clientName);
        log.info("Client " + clientName + " logged into ATM." + " Bank " + bankName + " was selected");

        resp.sendRedirect("/menu.html");
    }
}
