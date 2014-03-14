package com.luxoft.bankapp.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by User on 14.03.14.
 */
public class MenuServlet extends HttpServlet {

    private static Logger log = Logger.getLogger(MenuServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String account = req.getParameter("account");
        if (account == null) {
            log.warning("Account not found");
            throw new ServletException("No account specified.");
        }
        req.getSession().setAttribute("accountId", account);

    }

}
