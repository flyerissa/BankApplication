package com.luxoft.bankapp.test;

import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.service.bank.BankService;
import com.luxoft.bankapp.service.bank.TestService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by aili on 02.03.14.
 */
public class BankDAOTest {
    Bank bank;

    @Before
    public void initBank() throws ClientExistsException {
        bank = new Bank();
        bank.setName("My Bank");
        Client client = new Client();
        client.setFullName("Ivan Ivanov");
        client.setCity("Kiev");
        client.addAccount("C", 1000, 200);
        bank.addClient(client);
    }

    @Test
    public void testInsert() throws IllegalAccessException {
        BankService.saveBank(bank);

        Bank bank2 = BankService.loadBank();

        assertTrue(TestService.isEquals(bank, bank2));
    }


    @Test
    public void testUpdate() throws ClientExistsException, IllegalAccessException {
        BankService.saveBank(bank);

        // make changes to Bank
        Client client2 = new Client();
        client2.setFullName("Ivan Petrov");
        client2.setCity("New York");
        client2.addAccount("S", 5000, 0);
        bank.addClient(client2);
        BankService.saveBank(bank);

        Bank bank2 = BankService.loadBank();

        assertTrue(TestService.isEquals(bank, bank2));
    }
}
