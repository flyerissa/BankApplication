package com.luxoft.bankapp.domain.bank;

import java.io.Serializable;

//3d exercise
public interface ClientRegistrationListener extends Serializable {
    void onClientAdded(Client c);
}
