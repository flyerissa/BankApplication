package com.luxoft.bankapp.servlets;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by User on 14.03.14.
 */
public class SessionAmountServlet extends HttpServlet {
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = getServletContext();
        Integer amount = (Integer) context.getAttribute("clientsConnected");
        ServletOutputStream out = resp.getOutputStream();
        out.println("<html>");
        out.println("<head>");
        out.println("</head>");
        out.println("<body>");
        out.println("Hello! Im ATM SuperBank <br>");
        out.println("There are " + amount + "clients amount on server");
        out.println("</body>");
        out.println("</html>");
    }

}
