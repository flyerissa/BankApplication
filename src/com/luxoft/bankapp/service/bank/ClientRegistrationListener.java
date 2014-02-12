package com.luxoft.bankapp.service.bank;

import com.luxoft.bankapp.domain.bank.Client;

/**
 * Created by User on 12.02.14.
 */
public interface ClientRegistrationListener {
    void onClientAdded(Client c);
}
