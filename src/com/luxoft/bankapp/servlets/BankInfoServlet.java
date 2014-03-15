package com.luxoft.bankapp.servlets;

import com.luxoft.bankapp.domain.bank.BankInfo;
import com.luxoft.bankapp.exceptions.BankInfoException;
import com.luxoft.bankapp.service.bank.BankService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by aili on 15.03.14.
 */
public class BankInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String bankName = (String) req.getSession().getAttribute("bankName");

        try {
            BankInfo info = BankService.getInstance().getBankInfo(bankName);
            Integer numberOfClients = info.getNumberOfClients();
            Double totalAmount = info.getTotalAccountSum();
            req.getSession().setAttribute("numberOfClients", numberOfClients);
            req.getSession().setAttribute("totalSum", totalAmount);
            req.getRequestDispatcher("/jsp/secure/bankInfo.jsp").forward(req, resp);
        } catch (BankInfoException e) {
            e.printStackTrace();
        }
    }
}
