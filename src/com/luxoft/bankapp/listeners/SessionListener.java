package com.luxoft.bankapp.listeners;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by User on 14.03.14.
 */
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        final ServletContext context = httpSessionEvent.getSession().getServletContext();
        synchronized (SessionListener.class) {
            Integer clientsConnected = (Integer) context.getAttribute("clientsConnected");
            if (clientsConnected == null) {
                clientsConnected = 1;
            } else {
                clientsConnected++;
            }
            context.setAttribute("clientsConnected", clientsConnected);
        }
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        final ServletContext context = httpSessionEvent.getSession().getServletContext();
        synchronized (SessionListener.class) {
            Integer clientsConnected = (Integer) context.getAttribute("clientsConnected");
            clientsConnected--;
            context.setAttribute("clientsConnected", clientsConnected);
        }
    }
}
