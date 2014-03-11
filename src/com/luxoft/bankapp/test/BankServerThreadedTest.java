package com.luxoft.bankapp.test;

import com.luxoft.bankapp.domain.bank.Account;
import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.service.bank.BankService;

import static junit.framework.Assert.assertEquals;

/**
 * Created by User on 06.03.14.
 */
public class BankServerThreadedTest {
    public static void main(String[] args) throws Exception {
        Bank bank = BankService.getInstance().selectBank("Bank");
        Client clientTest = BankService.getInstance().selectClient(bank, "Test threads");
        Account active = BankService.getInstance().findAccountFromDB(clientTest, 103);

        double actual = active.getBalance();
        BankClientMock clientMock = null;
        for (int i = 0; i < 2; i++) {
            clientMock = new BankClientMock(clientTest, bank, active);
            clientMock.start();
        }
        for (int i = 0; i < 2; i++) {
            clientMock.join();
        }
        BankService.getInstance().saveOrUpdateClientToDB(clientTest);
        Double expected = active.getBalance();
        assertEquals(actual - 20, expected, 0.1);
    }

}
