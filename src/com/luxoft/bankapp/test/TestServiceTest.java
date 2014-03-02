package com.luxoft.bankapp.test;

import com.luxoft.bankapp.domain.bank.*;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.service.bank.TestService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by aili on 02.03.14.
 */
public class TestServiceTest {
    Bank bank1, bank2;
    Client client, client2;
    Account account1, account2;

    @Before
    public void initBanks() throws ClientExistsException {
        bank1 = new Bank();
        bank1.setId(1);
        bank1.setName("My Bank");

        client = new Client();
        client.setId(1);
        client.setFullName("Ivan Ivanov");
        client.setCity("Kiev");
        client.setOverdraft(300.0);
        client.setPhone("9999");
        client.setGender(Gender.MALE);
        client.setEmail("q@gmail.com");
        client.setBank(bank1);
        client.addAccount("C", 1000, 200);
        bank1.addClient(client);

        bank2 = new Bank();
        bank2.setId(1);
        bank2.setName("My Bank");

        client2 = new Client();
        client2.setId(2);
        client2.setFullName("Ivan Ivanov");
        client2.setCity("Kiev");
        client2.setOverdraft(300.0);
        client2.setPhone("9999");
        client2.setGender(Gender.MALE);
        client2.setEmail("q@gmail.com");
        client2.setBank(bank2);
        client2.addAccount("C", 1000, 200);
        bank2.addClient(client2);

        account1 = new CheckingAccount(500, 100);
        account1.setId(1);
        account1.setAccountType("checking");
        account1.setClientID(1);

        account2 = new CheckingAccount(500, 100);
        account2.setId(2);
        account2.setAccountType("checking");
        account2.setClientID(2);
    }

    @Test
    public void testBanksEqual() throws IllegalAccessException {
        assertTrue(TestService.isEquals(bank1, bank2));
    }

    @Test
    public void testClientsEqual() throws IllegalAccessException {
        assertTrue(TestService.isEquals(client, client2));
    }

    @Test
    public void testAccountsEqual() throws IllegalAccessException {
        assertTrue(TestService.isEquals(account1, account2));
    }

}
