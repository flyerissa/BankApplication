package com.luxoft.bankapp.servlets;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by aili on 13.03.14.
 */
public class WelcomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletOutputStream out = resp.getOutputStream();
        out.println("<html><body>");
        out.println("Hello! Im ATM <br>");
        out.println("<a href='/jsp/login.jsp'>Login as bank's client</a>");
        out.println("<a href='/jsp/office_login.jsp'>Login as remote office</a>");
        out.println("</body></html>");
    }
}
