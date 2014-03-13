package com.luxoft.bankapp.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by aili on 13.03.14.
 */
public class CheckLoggedFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();
        String clientName = (String) session.getAttribute("clientName");
        String path = ((HttpServletRequest) servletRequest).getRequestURI();
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (path.startsWith("/html/login") || path.equals("/welcome") || path.startsWith("/css/") || path.startsWith("/js/") || path.equals("/") || clientName != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendRedirect("/html/login.html");

        }
    }

    @Override
    public void destroy() {

    }
}
