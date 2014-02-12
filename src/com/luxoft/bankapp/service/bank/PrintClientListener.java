package com.luxoft.bankapp.service.bank;

import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.service.bank.ClientRegistrationListener;

/**
 * Created by User on 12.02.14.
 */
public class PrintClientListener implements ClientRegistrationListener {
    @Override
    public void onClientAdded(Client c) {
        System.out.println(c);
    }
}
