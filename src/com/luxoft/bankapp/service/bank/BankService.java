package com.luxoft.bankapp.service.bank;

import com.luxoft.bankapp.domain.bank.*;

/**
 * Created by User on 12.02.14.
 */
public class BankService {
    public static void addClient(Bank bank, Client client){
       bank.getClients().add(client);
    }
    public static  void printMaximumAmountToWithdraw(Bank b){
        for(Client c : b.getClients()){
            System.out.println(c.getActiveAccount().maximumAmountToWithdraw());
        }
    }
}
