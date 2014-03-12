package com.luxoft.bankapp.test;

import com.luxoft.bankapp.domain.bank.Account;
import com.luxoft.bankapp.domain.bank.Bank;
import com.luxoft.bankapp.domain.bank.Client;
import com.luxoft.bankapp.service.bank.BankService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;

/**
 * Created by User on 06.03.14.
 */
public class BankServerThreadedTest {
    private Bank bank;
    private Client clientTest;
    private Account active;
    double actual;
    List<Thread> threads = new ArrayList<Thread>();

    @Before
    public void init() throws Exception {
        bank = BankService.getInstance().selectBank("Bank");
        clientTest = BankService.getInstance().selectClient(bank, "Test threads");
        active = BankService.getInstance().findAccountFromDB(clientTest, 103);
        actual = active.getBalance();
    }

    @Test
    public void testThreads() throws InterruptedException {
        BankClientMock bankClientMock = new BankClientMock(clientTest, bank, active);
        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(bankClientMock);
            thread.start();
            threads.add(thread);
        }
        for (int i = 0; i < 3; i++) {
            threads.get(i).join();
        }
        double expected = active.getBalance();
        assertEquals(actual - 20, expected, 0.1);
    }
}
