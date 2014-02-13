package com.luxoft.bankapp.service.bank;

import com.luxoft.bankapp.domain.bank.Client;

public interface ClientRegistrationListener {
    void onClientAdded(Client c);
}
