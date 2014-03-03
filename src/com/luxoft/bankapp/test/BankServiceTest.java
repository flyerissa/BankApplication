package com.luxoft.bankapp.test;

import com.luxoft.bankapp.domain.bank.Account;
import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.service.bank.BankService;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by User on 03.03.14.
 */
public class BankServiceTest {
    Bank bank1, bank2;
    Client client, client2;
    Account account1, account2;

    @Test
    public void testPrintMaximumAmountToWithdraw() {
        bank1 = new Bank();
        bank1.setName("Big Bank");
        client = new Client();
        client.setFullName("Ivan Ivanov");
        client.addAccount("C", 1000, 300);
        try {
            bank1.addClient(client);

        } catch (ClientExistsException e) {
            e.printStackTrace();
        }
        //assertTrue(bank1.getClients() != null);
        // assertEquals(client.getActiveAccount().maximumAmountToWithdraw(), 1300.0);
        assertTrue(bank1.getClients() != null);
        assertEquals(client.getActiveAccount().maximumAmountToWithdraw(), 1300.0);
    }

    @Test
    public void testDepositAccount() {
        client = new Client();
        Account account = client.addAccount("C", 5000, 300);
        BankService.getInstance().depositAccount(client, 1000);
        assertEquals(6000.0, account.getBalance());

    }

    @Test
    public void testWithdrawAccount() throws NotEnoughFundsException {
        client = new Client();
        Account account = client.addAccount("C", 5000, 300);
        BankService.getInstance().withdrawAccount(client, 1000);
        assertEquals(4000.0, account.getBalance());
    }

    @Test
    public void testTransfer() throws NotEnoughFundsException {
        client = new Client();
        Account account = client.addAccount("C", 5000, 300);
        client2 = new Client();
        Account account3 = client2.addAccount("C", 1000, 300);
        BankService.getInstance().transfer(client, 500.0, client2);
        assertEquals(4500.0, account.getBalance());
        assertEquals(1500.0, account3.getBalance());
    }


}
